package com.example.testapp.common.rest.interceptors

import android.content.Context
import com.example.testapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request()?.newBuilder()?.addHeader("Authorization", BuildConfig.API_KEY)?.build()
        return chain?.proceed(request)
    }
}