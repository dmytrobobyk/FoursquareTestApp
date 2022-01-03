package com.example.testapp.models

import com.google.gson.annotations.SerializedName

data class Parent(
    @SerializedName("fsq_id")val fsqId: String,
    val name: String
)