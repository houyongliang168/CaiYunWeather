package com.hyl.owncaiyunweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication :Application() {
    companion object{

        lateinit var context:Context
//        彩虹令牌值
        const val TOKEN="gIygfiove4XutYwg"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}