package com.jetpack.alarmmanagerwithnotification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.alarmmanagerwithnotification.ui.theme.AlarmManagerWithNotificationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://tesapp123.herokuapp.com/"

class MainActivity : AppCompatActivity() {
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMydata()
        val btnClick : Button = findViewById(R.id.btn_click)
        textView = findViewById(R.id.tv_text)
//
//        btnClick.setOnClickListener{
//
//        }

    }

    private fun getMydata() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>){
                val responseBody= response.body()!!
             var myStringBuilder = StringBuilder()
                for(myData in responseBody){
                    if (myStringBuilder.isEmpty()) {
                        myStringBuilder= myStringBuilder.append(myData.lama_dering)
                    }else{
                        textView.text = myStringBuilder.toString()
                        setAlarm(myStringBuilder.toString().toInt())
                    }
                }
            }
            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity","onFailure"+t.message)
            }
        })
    }

    private fun setAlarm(setTimeAPi : Int) {

        val timeSec = System.currentTimeMillis() + (setTimeAPi * 1000)
        val alarmManager = baseContext.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(baseContext, MyAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(baseContext, 0, intent,  0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeSec, pendingIntent)
    }
}




















