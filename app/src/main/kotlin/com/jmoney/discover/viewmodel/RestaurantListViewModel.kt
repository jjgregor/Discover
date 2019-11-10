package com.jmoney.discover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmoney.discover.datamodel.RestaurantListState
import com.jmoney.discover.interfaces.Schedulers
import com.jmoney.domain.repository.RestaurantRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 37.422740
private const val DEFAULT_LONGITUDE = -122.139956

class RestaurantListViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val schedulers: Schedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<RestaurantListState>()
    val state: LiveData<RestaurantListState> = _state

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getRestaurantsFromCoordinates(
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE
    ) {
        _state.postValue(RestaurantListState.Loading)

        restaurantRepository.getRestaurants(latitude, longitude)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(
                onSuccess = { _state.postValue(RestaurantListState.Restaurants(it))},
                onError = {
                    _state.postValue(RestaurantListState.Error)
                    Timber.e(it, "Error getting restaurants")
                }
            ).addTo(compositeDisposable)
    }

}