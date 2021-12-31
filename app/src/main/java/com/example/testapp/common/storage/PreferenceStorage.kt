package com.example.testapp.common.storage

interface PreferenceStorage {
    fun saveFavoritePlace(placeName: String)
    fun getFavoritePlace(): Set<String>
//    fun removeFavoritePlace(placeName: String)
}