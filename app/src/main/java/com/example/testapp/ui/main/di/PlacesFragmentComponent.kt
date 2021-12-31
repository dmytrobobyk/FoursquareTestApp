package com.example.testapp.ui.main.di

import com.example.testapp.di.annotations.FragmentScope
import com.example.testapp.ui.di.MainComponent
import com.example.testapp.ui.main.fragments.PlacesFragment
import dagger.Component

@FragmentScope
@Component(modules = [PlacesFragmentModule::class], dependencies = [MainComponent::class])
interface PlacesFragmentComponent {
    fun inject(fragment: PlacesFragment)
}