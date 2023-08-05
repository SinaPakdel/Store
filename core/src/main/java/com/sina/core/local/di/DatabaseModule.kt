package com.sina.core.local.di

import android.app.Application
import androidx.room.Room
import com.sina.core.local.di.annotation.DatabaseName
import com.sina.core.local.db.CustomerDao
import com.sina.core.local.db.StoreDatabase
import com.sina.core.local.db.OrderDao
import com.sina.core.local.params.LocalParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName(): String = LocalParams.DATABASE_NAME

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        @DatabaseName dataBase: String,
    ): StoreDatabase =
        Room.databaseBuilder(application, StoreDatabase::class.java, dataBase)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOrderDao(appDatabase: StoreDatabase): OrderDao = appDatabase.orderDao()

    @Provides
    @Singleton
    fun provideCustomerDao(appDatabase: StoreDatabase): CustomerDao = appDatabase.customerDao()
}