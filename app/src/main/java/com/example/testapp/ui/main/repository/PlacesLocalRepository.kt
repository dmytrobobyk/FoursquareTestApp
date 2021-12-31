package com.example.testapp.ui.main.repository

import com.example.testapp.ui.main.rest.PlacesApi
import com.example.testapp.models.Result
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import io.reactivex.Single

class PlacesLocalRepository(private val api: PlacesApi) : PlacesRepository {
    override fun searchPlaces(query: String): Single<List<Result>> {
        return api.searchPlaces(query = query)
            .map { it.results }
    }
}