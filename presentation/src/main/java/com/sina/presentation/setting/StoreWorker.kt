package com.sina.presentation.setting

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sina.core.common.permission.PermissionManager
import com.sina.presentation.notification.NotificationHelper
import com.sina.core.network.worker.WorkerNetworkManager
import timber.log.Timber

class StoreWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private lateinit var notificationHelper: NotificationHelper

    override suspend fun doWork(): Result {
        val inputData = inputData.getString("DATA_NAME") ?: "21"
        Timber.e("doWork,inputData:$inputData")
        val date = createCustomTime(inputData.toInt())
        Timber.e("doWork,date:$date")
        val newProduct = WorkerNetworkManager.service.getNewProductList(date)
        Timber.e("doWork,newProduct:$newProduct")
        if (newProduct.isSuccessful) {
            newProduct.body()?.let {
                Timber.e("Successful:${it.first().name}")
                notificationHelper = NotificationHelper(context)
                notificationHelper.navigateWithArgsTo(it.first().id.toString())
                if (PermissionManager.checkNotificationPermission(context)) {
                    notificationHelper.showNotification(
                        it.first().name.toString(),
                        it.first().description.toString()
                    )
                    Timber.e("doWork,newProduct,name:${it.first().name.toString()}")
                    Timber.e("doWork,newProduct,description:${it.first().description.toString()}")
                } else {
                    PermissionManager.requestNotificationPermission()
                    Timber.e("Not permission")
                }
            }
        }
        return Result.success()
    }

    private fun createCustomTime(time: Int): String {
        val currentTime = Calendar.getInstance()
        currentTime.add(/*change if u want set for hours:*/Calendar.DAY_OF_WEEK_IN_MONTH, -time)
        return SimpleDateFormat(/* pattern = */ "yyyy-MM-dd'T'HH:mm:ss").format(currentTime.time)
    }
}