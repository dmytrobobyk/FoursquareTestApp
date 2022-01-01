package com.example.testapp.ui.main.repository

import io.reactivex.Single
import com.example.testapp.models.Result
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import io.reactivex.Completable


interface PlacesRepository {
    fun searchPlaces(query: String): Single<List<PlaceDetailsInfo>>
    fun addFavoritePlace(placeName: String): Completable
}