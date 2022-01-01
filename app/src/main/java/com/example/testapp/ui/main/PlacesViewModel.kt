package com.example.testapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.models.Result
import com.example.testapp.common.viewmodel.base.BaseViewModel
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.example.testapp.ui.main.repository.PlacesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class PlacesViewModel(private val repository: PlacesRepository) : BaseViewModel() {


    private val requestProcessor: PublishProcessor<String> = PublishProcessor.create()
    private lateinit var debounceDisposable: Disposable
    private val SEARCH_DURATION = 200L

//    private val _placesList = MediatorLiveData<List<Result>>()
//    val placesList: MutableLiveData<List<Result>> get() = _placesList

    private val _placesList = MediatorLiveData<List<PlaceDetailsInfo>>()
    val placesList: MutableLiveData<List<PlaceDetailsInfo>> get() = _placesList

    private val _isLoading = MediatorLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _placeholderVisibility = MediatorLiveData<Boolean>().apply { value = true }
    val placeholderVisibility: LiveData<Boolean> get() = _placeholderVisibility


    fun onSearchTextChange(text: CharSequence) {
        if (!::debounceDisposable.isInitialized) {
            debounceDisposable = requestProcessor
                .debounce(SEARCH_DURATION, TimeUnit.MILLISECONDS)
                .flatMap { text ->
                    repository.searchPlaces(text)
                        .doOnSubscribe {
                            _isLoading.postValue(true)
                            _placeholderVisibility.postValue(false)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toFlowable()
                }
                .subscribe({
                    _isLoading.postValue(false)
                    _placeholderVisibility.postValue(false)
                    _placesList.postValue(it)
                },
                    {
                        _isLoading.postValue(false)
                        _placeholderVisibility.postValue(true)
                        it.printStackTrace()
                    })
                .apply { addSubscription(this) }
        }
        if (text.isEmpty()) {
            _placesList.postValue(emptyList())
            _isLoading.postValue(false)
            _placeholderVisibility.postValue(true)
        } else {
            requestProcessor.onNext(text.toString())
        }
    }
}