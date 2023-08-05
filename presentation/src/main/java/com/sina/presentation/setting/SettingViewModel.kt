package com.sina.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sina.core.common.constants.Constants.Companion.ID
import com.sina.core.local.datastore.AppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val workManager: WorkManager,
    private var periodicWorkRequest: PeriodicWorkRequest,
    private val appDataStore: AppDataStore
) : ViewModel() {

    var currentTime = "3"
    private val data = Data.Builder().putString("DATA_NAME", currentTime)


    fun setTime(time: Int) {

        periodicWorkRequest =
            PeriodicWorkRequestBuilder<StoreWorker>(time.toLong(), TimeUnit.HOURS)
                .setInputData(data.build())
                .build()
        setWorkManager()
    }

    fun stopService() {
        workManager.cancelUniqueWork(ID)
    }

    fun startService() {
        periodicWorkRequest = PeriodicWorkRequestBuilder<StoreWorker>(currentTime.toLong(), TimeUnit.HOURS)
            .setInputData(data.build())
            .build()
        setWorkManager()
    }

    private fun setWorkManager() {
        workManager.enqueueUniquePeriodicWork(
            ID,
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    fun userLoggedOut() {
        viewModelScope.launch(Dispatchers.IO) {
            appDataStore.saveUserLoggedIn(false)
        }
    }
}