package com.sina.justore.di

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.sina.presentation.setting.StoreWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    @Singleton
    fun providesWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideWorkRequest(): WorkRequest =
        PeriodicWorkRequestBuilder<StoreWorker>(
            15,
            TimeUnit.MINUTES
        ).build()

    @Provides
    fun providePeriodicWorkRequest(workRequest: WorkRequest): PeriodicWorkRequest {
        return workRequest as PeriodicWorkRequest
    }
}