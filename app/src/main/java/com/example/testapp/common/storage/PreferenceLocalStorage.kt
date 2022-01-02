package com.example.testapp.common.storage

import android.content.Context

class PreferenceLocalStorage(private val context: Context) : PreferenceStorage {

    override fun saveFavoritePlace(placeName: String) {
        val sharedPreferences = context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var favorites: MutableSet<String> = sharedPreferences.getStringSet(FAVORITES_KEY,
            mutableSetOf()) as MutableSet<String>
        if(!favorites.contains(placeName)){
            favorites.add(placeName)
            editor.putStringSet(FAVORITES_KEY, favorites)
        } else {
            favorites.remove(placeName)
        }
        editor.apply()
    }

    override fun getFavoritePlaces(): Set<String> {
        val sharedPreferences = context.getSharedPreferences(FAVORITES_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet(FAVORITES_KEY, mutableSetOf()) as MutableSet<String>
    }

    companion object {
        const val FAVORITES_KEY = "Favorites_key"
    }
}