package com.example.testapp.ui.main.repository

import com.example.testapp.common.storage.PreferenceStorage
import com.example.testapp.common.util.getDistanceToSeattle
import com.example.testapp.common.util.getImageUrl
import com.example.testapp.common.util.getPlaceCategory
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.example.testapp.ui.main.rest.PlacesApi
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class PlacesLocalRepository(private val api: PlacesApi, private val preferenceStorage: PreferenceStorage) : PlacesRepository {
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
                            getImageUrl(it.categories),
                            isItemFavorite(it.name)
                        )
                    }.toList()
            }
    }

    override fun addFavoritePlace(placeName: String): Completable {
        return Completable.fromAction {
            preferenceStorage.saveFavoritePlace(placeName)
        }
    }

    private fun isItemFavorite(itemName : String): Boolean {
        val favorites = preferenceStorage.getFavoritePlaces()
        favorites.forEach {
            if (it == itemName) {
                return true
            }
        }
        return false
    }
}