package com.sina.core.network.di

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sina.core.network.params.ServerParams.BASE_URL
import com.sina.core.network.params.ServerParams.CONSUMER_KEY
import com.sina.core.network.params.ServerParams.CONSUMER_KEY_VALUE
import com.sina.core.network.params.ServerParams.CONSUMER_SECRET
import com.sina.core.network.params.ServerParams.CONSUMER_SECRET_VALUE
import com.sina.core.network.di.annotation.BaseUrl
import com.sina.core.network.di.annotation.ConsumerKey
import com.sina.core.network.di.annotation.ConsumerSecret
import com.sina.core.network.di.annotation.ExcludeInSerialization
import com.sina.core.network.di.tools.provideApi
import com.sina.core.network.services.StoreServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ConsumerKey
    fun provideConsumerKey(): String = CONSUMER_KEY

    @Provides
    @Singleton
    @ConsumerSecret
    fun provideConsumerSecret(): String = CONSUMER_SECRET

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideInterceptor(@ConsumerKey consumerKey: String, @ConsumerSecret consumerSecret: String): Interceptor =
        Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(CONSUMER_KEY_VALUE, consumerKey)
                .addQueryParameter(CONSUMER_SECRET_VALUE, consumerSecret)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideOkHttpLog(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .addSerializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes?): Boolean =
                f?.getAnnotation(ExcludeInSerialization::class.java) != null

            override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
        })
        .create()

    @Singleton
    @Provides
    fun jsonConvertorFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit) = provideApi<StoreServices>(retrofit)
}