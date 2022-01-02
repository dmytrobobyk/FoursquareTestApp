package com.example.testapp.common.util

import com.example.testapp.models.Category


private val IMAGE_SIZE_BIG = 88
private val IMAGE_SIZE_MEDIUM = 64
private val IMAGE_SIZE_SMALL = 32


fun getPlaceCategory(categories: List<Category>): String {
    return if(categories.isEmpty()) {
        "No category"
    } else {
        categories[0].name
    }
}

fun getImageUrl(categories: List<Category>): String {
    return if(categories.isEmpty()) {
        ""
    } else {
        "${categories[0].icon.prefix}${IMAGE_SIZE_BIG}${categories[0].icon.suffix}"
    }
}

fun isItemFavorite(itemName : String, favorites: Set<String>): Boolean {
    favorites.forEach {
        if (it == itemName) {
            return true
        }
    }
    return false
}