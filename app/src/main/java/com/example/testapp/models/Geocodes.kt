package com.example.testapp.models

import com.google.gson.annotations.SerializedName

data class Geocodes(
    @SerializedName("front_door") val frontDoor: FrontDoor,
    val main: Main
)