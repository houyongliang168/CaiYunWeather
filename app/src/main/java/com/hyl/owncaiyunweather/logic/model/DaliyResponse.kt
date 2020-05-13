package com.hyl.owncaiyunweather.logic.model


import com.google.gson.annotations.SerializedName

// 未来几天的天气数据 {} 包含内部 防止模型类名冲突 eg  result
data class DaliyResponse(val status: String, val result: Result) {
    //@SerializedName json 与 kotlin 字段建立映射关系

    data class Result(
        val daily: Daily
    )

    data class Daily(
        val temperature: List<Temperature>, val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Skycon(val value: String, val date: java.util.Date)
    data class Temperature(val max: Float, val min: Float)
    data class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)

}

