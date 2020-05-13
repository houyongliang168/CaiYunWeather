package com.hyl.owncaiyunweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    suspend fun getRealtimeWeather(lng: String,lat:String) = weatherService.getRealtimeWeather(lng,lat).await()
    suspend fun getDailyWeather(lng: String,lat:String) = weatherService.getDailyWeather(lng,lat).await()

    //
    private suspend fun <T> Call<T>.await(): T {
        //协成 挂起
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    //协成 执行
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    //协成 执行
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("Response body is null"))
                }

            }
            )
        }
    }
}