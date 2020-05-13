package com.hyl.owncaiyunweather.logic

import androidx.lifecycle.liveData
import com.hyl.owncaiyunweather.logic.dao.PlaceDao
import com.hyl.owncaiyunweather.logic.model.Place
import com.hyl.owncaiyunweather.logic.model.Weather
import com.hyl.owncaiyunweather.logic.network.SunWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaliy = async {
                SunWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaliy.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }

    }


    fun savePlace(place: Place) {
        PlaceDao.savePlace(place)
    }

    fun getSavedPlace(): Place = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    private fun <T> fire(context: CoroutineContext, bolck: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                bolck()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}

//    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = SunWeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
//                Result.success(places)
//            } else {
////                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        // 执行发送信息  类似 livedata post
//        emit(result)
//
//    }

//    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            coroutineScope {
//                val deferredRealtime = async {
//                    SunWeatherNetwork.getRealtimeWeather(lng, lat)
//                }
//                val deferredDaliy = async {
//                    SunWeatherNetwork.getDailyWeather(lng, lat)
//                }
//                val realtimeResponse = deferredRealtime.await()
//                val dailyResponse = deferredDaliy.await()
//
//                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
//                    val weather =
//                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
//                    Result.success(weather)
//                } else {
////                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"+
////                        "daily response status is ${dailyResponse.status}"))
//                    Result.failure<Weather>(
//                        RuntimeException(
//                            "realtime response status is ${realtimeResponse.status}" +
//                                    "daily response status is ${dailyResponse.status}"
//                        )
//                    )
//                }
//
//            }
//
//        } catch (e: Exception) {
//            Result.failure<Weather>(e)
//        }
//        // 执行发送信息  类似 livedata post
//        emit(result)
//
//    }