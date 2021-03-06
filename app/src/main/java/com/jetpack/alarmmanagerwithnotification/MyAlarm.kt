package com.jetpack.alarmmanagerwithnotification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

class MyAlarm: BroadcastReceiver() {
    companion object {
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        // Siapkan 2 id untuk 2 macam alarm, onetime dan repeating
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"

    }
    override fun onReceive(context: Context, intent: Intent) {
//        try {
//            showNotification(context, "Make it Easy", "Compose Notification")
//        } catch (ex: Exception) {
//            Log.d("Receive Ex", "onReceive: ${ex.printStackTrace()}")
//        }
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val title = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notifId = if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        if (message != null) {
            showAlarmNotification(context, title, message, notifId)
        }

    }
    fun setRepeatingAlarm(context: Context,type: String, message: String) {
//        if (isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyAlarm::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        val putExtra = intent.putExtra(EXTRA_TYPE, type)
//        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        Log.d("cek",time)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_access_time_black)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }
}


//private fun showNotification(context: Context, title: String, desc: String) {
//    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    val channelId = "message_channel"
//    val channelName = "message_name"
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//        manager.createNotificationChannel(channel)
//    }
//
//    val builder = NotificationCompat.Builder(context, channelId)
//        .setContentTitle(title)
//        .setContentText(desc)
//        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
//
//    manager.notify(1, builder.build())
//}












