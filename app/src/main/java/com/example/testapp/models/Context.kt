package com.example.testapp.models

import com.google.gson.annotations.SerializedName

data class Context(
    @SerializedName("geo_bounds")val geoBounds: GeoBounds
)