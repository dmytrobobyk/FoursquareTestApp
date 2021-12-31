package com.example.testapp.di

import android.content.Context
import com.example.testapp.common.storage.PreferenceLocalStorage
import com.example.testapp.common.storage.PreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context = context

    @Provides
    @Singleton
    fun localStorage(context: Context): PreferenceStorage = PreferenceLocalStorage(context)
}