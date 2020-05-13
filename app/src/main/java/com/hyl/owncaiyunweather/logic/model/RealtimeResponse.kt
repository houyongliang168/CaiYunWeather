package com.hyl.owncaiyunweather.logic.model

import com.google.gson.annotations.SerializedName

// 实时的数据
data class RealtimeResponse(val status: String, val result: Result){
    //@SerializedName json 与 kotlin 字段建立映射关系

    data class Result(
        val realtime: Realtime
    )

    data class Realtime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val ariQuality: QriQuality
    )

    data class QriQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}

