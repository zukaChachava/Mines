package com.lashaandzura.mines.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.lashaandzura.mines.constants.Constants
import com.lashaandzura.mines.notifications.NotificationUtil
import kotlin.math.log

class MineReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if(intent.getBooleanExtra(Constants.BROADCAST_KEY, true)){
            NotificationUtil.showNotification(context!!, "გილოცავ, მოიგე. შენი რესულტატი შენახულია ჩვენს ბაზაში... სამუდამოდ")
            return
        }
        NotificationUtil.showNotification(context!!, "სამწუხაროდ, დამარცხდი. შენი რესულტატი შენახულია ჩვენს ბაზაში... სამუდამოდ")
    }
}