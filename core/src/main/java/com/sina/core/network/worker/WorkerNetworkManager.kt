package com.sina.core.network.worker

import com.sina.core.network.params.ServerParams.BASE_URL
import com.sina.network.services.WorkerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WorkerNetworkManager {

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(220, TimeUnit.SECONDS)
        .build()

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    val service: WorkerApi = retrofit.create(WorkerApi::class.java)

}