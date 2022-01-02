package com.example.testapp.common.storage

interface PreferenceStorage {
    fun saveFavoritePlace(placeName: String)
    fun getFavoritePlaces(): Set<String>
}