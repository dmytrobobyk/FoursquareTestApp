package com.example.testapp.models

import com.google.gson.annotations.SerializedName

data class Location(
    val address: String,
    @SerializedName("address_extended")val addressExtended: String,
    val country: String,
    val cross_street: String,
    val dma: String,
    val locality: String,
    val neighborhood: List<String>,
    val po_box: String,
    val postcode: String,
    val region: String
)