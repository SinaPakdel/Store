package com.sina.core.common.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


object PermissionManager {
    private const val REQUEST_CODE_NOTIFICATION_PERMISSION = 1313
    private lateinit var currentActivity: Activity

    fun initialize(activity: Activity) {
        currentActivity = activity
    }

    fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            currentActivity,
            arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
            REQUEST_CODE_NOTIFICATION_PERMISSION
        )
    }

    fun checkNotificationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY
        ) == PackageManager.PERMISSION_GRANTED
    }
}