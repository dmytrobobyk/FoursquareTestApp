package com.example.testapp.common.rest

import android.content.Context
import com.example.testapp.common.rest.interceptors.ConnectionInterceptor
import com.example.testapp.BuildConfig
import com.example.testapp.common.rest.interceptors.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun <T> createService(clazz: Class<T>, context: Context) = Retrofit.Builder()
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.API_ENDPOINT)
    .client(
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .addInterceptor(ConnectionInterceptor(context))
            .addInterceptor(AuthorizationInterceptor(context))
            .build()
    )
    .build()
    .create(clazz)

private fun loggingInterceptor() =
    HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)