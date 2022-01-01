package com.example.testapp.ui.main.repository

import com.example.testapp.common.util.getDistanceToSeattle
import com.example.testapp.common.util.getImageUrl
import com.example.testapp.common.util.getPlaceCategory
import com.example.testapp.ui.main.rest.PlacesApi
import com.example.testapp.models.Result
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import io.reactivex.Flowable
import io.reactivex.Single

class PlacesLocalRepository(private val api: PlacesApi) : PlacesRepository {
    override fun searchPlaces(query: String): Single<List<PlaceDetailsInfo>> {
        return api.searchPlaces(query = query)
            .map { it.results }
            .flatMap {
                Flowable.fromArray(it)
                    .flatMapIterable { list -> list }
                    .map { it ->
                        PlaceDetailsInfo(
                            it.name,
                            getDistanceToSeattle(it.geocodes.main.latitude, it.geocodes.main.longitude),
                            getPlaceCategory(it.categories),
                            it.fsq_id,
                            it.geocodes.main.latitude,
                            it.geocodes.main.longitude,
                            it.location.address,
                            it.location.country,
                            it.location.postcode,
                            it.location.region,
                            getImageUrl(it.categories)
                        )
                    }.toList()
            }
    }
}