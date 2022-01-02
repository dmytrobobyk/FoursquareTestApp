package com.example.testapp.ui.di

import android.content.Context
import com.example.testapp.common.storage.PreferenceStorage
import com.example.testapp.di.AppComponent
import com.example.testapp.di.annotations.ActivityScope
import com.example.testapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainModule::class])
interface MainComponent {
    fun inject(target: MainActivity)

    fun context(): Context

    fun activity(): MainActivity

    fun localStorage(): PreferenceStorage

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: MainActivity): Builder

        fun appComponent(component: AppComponent): Builder

        fun plus(module: MainModule): Builder

        fun build(): MainComponent
    }
}