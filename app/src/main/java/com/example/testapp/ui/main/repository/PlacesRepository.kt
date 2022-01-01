package com.example.testapp.ui.main.repository

import io.reactivex.Single
import com.example.testapp.models.Result
import com.example.testapp.ui.details.models.PlaceDetailsInfo


interface PlacesRepository {
    fun searchPlaces(query: String): Single<List<PlaceDetailsInfo>>
}