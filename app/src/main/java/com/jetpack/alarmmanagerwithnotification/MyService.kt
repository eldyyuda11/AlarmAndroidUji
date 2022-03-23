package com.jetpack.alarmmanagerwithnotification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    private lateinit var alarmReceiver: MyAlarm
    companion object {
        internal val TAG = MyService::class.java.simpleName
        // Siapkan 2 id untuk 2 macam alarm, onetime dan repeating
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"

    }
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service dijalankan...")
        serviceScope.launch {
            alarmReceiver = MyAlarm()
            val repeatMessage = "tes1"
            alarmReceiver.setRepeatingAlarm(baseContext, MyAlarm.TYPE_REPEATING,repeatMessage)
//            delay(30000)
//            stopSelf()
//            Log.d(TAG, "Service dihentikan")
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: ")
    }
}