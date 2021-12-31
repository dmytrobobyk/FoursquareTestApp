package com.example.testapp.models

data class Location(
    val address: String,
    val address_extended: String,
    val country: String,
    val cross_street: String,
    val dma: String,
    val locality: String,
    val neighborhood: List<String>,
    val po_box: String,
    val postcode: String,
    val region: String
)