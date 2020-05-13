package com.hyl.owncaiyunweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hyl.owncaiyunweather.logic.Repository
import com.hyl.owncaiyunweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    //searchLiveData 来接受转换后的 livedata
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) {
        Repository.savePlace(place)
    }

    fun getSavedPlace(): Place = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}