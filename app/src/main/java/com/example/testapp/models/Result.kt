package com.example.testapp.models

import com.google.gson.annotations.SerializedName

data class Result(
    val name: String,
    val categories: List<Category>,
    val chains: List<Any>,
    val distance: Int,
    @SerializedName("fsq_id")val fsqId: String,
    val geocodes: Geocodes,
    val location: Location,
    val related_places: RelatedPlaces,
    val timezone: String
)