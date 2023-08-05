package com.sina.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.sina.core.common.permission.PermissionManager
import com.sina.presentation.R

class NotificationHelper(
    private val context: Context,
) {

    private val channelId = "my_channel_id"
    private val channelName = "My Channel"
    private lateinit var pendingIntent: PendingIntent

    init {
        createNotificationChannel()
    }


    fun navigateWithArgsTo(argumentValue: String) {
        pendingIntent = NavDeepLinkBuilder(context).setGraph(R.navigation.nav_graph)
            .setArguments(bundleOf("productId" to argumentValue.toInt()))
            .setDestination(R.id.detailsFragment)
            .createPendingIntent()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String) {
        val notificationId = System.currentTimeMillis().toInt()

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(com.sina.core.R.drawable.ic_shop)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        if (!PermissionManager.checkNotificationPermission(context)) {
            PermissionManager.requestNotificationPermission()
            return
        }
        notificationManagerCompat.notify(notificationId, builder.build())
    }
}