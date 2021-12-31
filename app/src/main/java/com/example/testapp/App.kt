package com.example.testapp

import android.app.Application
import com.example.testapp.di.AppModule
import com.example.testapp.di.DaggerAppComponent

class App : Application() {

    val component by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .plus(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}