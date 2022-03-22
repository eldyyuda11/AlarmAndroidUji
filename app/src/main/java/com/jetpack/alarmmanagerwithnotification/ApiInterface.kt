package com.jetpack.alarmmanagerwithnotification

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {


    @GET("/index")
    fun getData(): Call<List<MyDataItem>>

}