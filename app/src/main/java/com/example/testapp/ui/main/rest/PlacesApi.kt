package com.example.testapp.ui.main.rest

import com.example.testapp.BuildConfig
import com.example.testapp.models.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PlacesApi {

    @Headers("Authorization: fsq3zpx+htusk9C2OFI5PyndHcrqLeRvf1JfmrphsbqmNnU=")
    @GET("search")
    fun searchPlaces(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("v") apiVersion: String = BuildConfig.v,
        @Query("near") near: String = BuildConfig.near,
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): Single<SearchResponse>
}