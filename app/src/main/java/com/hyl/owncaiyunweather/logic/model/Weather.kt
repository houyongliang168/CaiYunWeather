package com.hyl.owncaiyunweather.logic.model

data class Weather(
    val realtime: RealtimeResponse.Realtime,
    val daily: DaliyResponse.Daily
)