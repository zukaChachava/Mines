package com.lashaandzura.mines.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.lashaandzura.mines.R

object NotificationUtil {
    const val CHANNEL_ID = "ZuraChannel"
    private var notificationID = 1

    fun showNotification(context: Context, text: String){
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bomb)
            .setContentTitle("დეპეშააა, დეპეშა")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID++, notification.build())
    }
}