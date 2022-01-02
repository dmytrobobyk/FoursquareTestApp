package com.example.testapp.ui.main.di

import android.content.Context
import com.example.testapp.common.rest.createService
import com.example.testapp.common.storage.PreferenceStorage
import com.example.testapp.di.annotations.FragmentScope
import com.example.testapp.ui.main.PlacesViewModel
import com.example.testapp.ui.main.repository.PlacesLocalRepository
import com.example.testapp.ui.main.repository.PlacesRepository
import com.example.testapp.ui.main.rest.PlacesApi
import dagger.Module
import dagger.Provides

@Module
class PlacesFragmentModule {

    @Provides
    @FragmentScope
    fun api(context: Context) = createService(PlacesApi::class.java, context)

    @Provides
    @FragmentScope
    fun repository(api: PlacesApi, preferenceStorage: PreferenceStorage): PlacesRepository = PlacesLocalRepository(api, preferenceStorage)

    @Provides
    @FragmentScope
    fun viewModel(repository: PlacesRepository) = PlacesViewModel(repository)
}