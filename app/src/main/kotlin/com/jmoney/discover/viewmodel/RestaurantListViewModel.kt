package com.jmoney.discover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmoney.discover.datamodel.RestaurantListState
import com.jmoney.discover.interfaces.Schedulers
import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.domain.repository.LikedRestaurantsRepository
import com.jmoney.domain.repository.RestaurantRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

private const val RESTAURANT_REQUEST_LIMIT = 25
private const val DEFAULT_LATITUDE = 37.422740
private const val DEFAULT_LONGITUDE = -122.139956

class RestaurantListViewModel @Inject constructor(
    private val restaurantRepository: RestaurantRepository,
    private val likedRestaurantRepository: LikedRestaurantsRepository,
    private val schedulers: Schedulers
) : ViewModel() {

    private var pageOffset = 0
    private var isDownloadingNextPage = false
    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<RestaurantListState>()
    val state: LiveData<RestaurantListState> = _state

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getRestaurantsFromCoordinates(
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE,
        pageLimit: Int = RESTAURANT_REQUEST_LIMIT
    ) {
        _state.postValue(RestaurantListState.Loading)

        restaurantRepository.getRestaurants(
            latitude = latitude,
            longitude = longitude,
            limit = pageLimit,
            offset = pageOffset
        )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(
                onSuccess = { onGetRestaurantsSuccess(it) },
                onError = { onGetRestaurantsError(it) }
            ).addTo(compositeDisposable)
    }

    fun shouldDownLoadNextPage(totalCount: Int, firstVisible: Int, visibleCount: Int) {
        if (!isDownloadingNextPage && visibleCount + firstVisible >= totalCount) {
            isDownloadingNextPage = true
            _state.postValue(RestaurantListState.Loading)
            getRestaurantsFromCoordinates()
        }
    }

    private fun onGetRestaurantsSuccess(restaurants: List<Restaurant>) {
        pageOffset += 1
        isDownloadingNextPage = false
        _state.postValue(RestaurantListState.Restaurants(restaurants))
    }

    private fun onGetRestaurantsError(throwable: Throwable) {
        _state.postValue(RestaurantListState.Error)
        Timber.e(throwable, "Error getting restaurants")
    }

    fun setRestaurantLiked(restaurantId: Long) {
        likedRestaurantRepository.setLikedRestaurant(restaurantId)
    }
}