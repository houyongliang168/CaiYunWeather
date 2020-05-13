package com.hyl.owncaiyunweather.logic.network

import com.hyl.owncaiyunweather.SunnyWeatherApplication
import com.hyl.owncaiyunweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_cn")
    fun searchPlaces(@Query("query") query: String) :Call<PlaceResponse>
}