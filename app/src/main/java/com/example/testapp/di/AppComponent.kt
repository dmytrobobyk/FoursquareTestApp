package com.example.testapp.di

import android.content.Context
import com.example.testapp.App
import com.example.testapp.common.storage.PreferenceStorage
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target: App)

    fun app(): App

    fun context(): Context

    fun localStorage(): PreferenceStorage

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(app: App): Builder

        fun plus(module: AppModule): Builder

        fun build(): AppComponent
    }
}