package com.example.testapp.models

data class Result(
    val name: String,
    val categories: List<Category>,
    val chains: List<Any>,
    val distance: Int,
    val fsq_id: String,
    val geocodes: Geocodes,
    val location: Location,
    val related_places: RelatedPlaces,
    val timezone: String
)