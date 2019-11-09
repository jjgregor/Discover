package com.jmoney.domain.repository

import com.jmoney.domain.datamodel.Restaurant
import io.reactivex.Single

interface RestaurantRepository {

    fun getRestaurants(lat: Long, lng: Long): Single<List<Restaurant>>
    fun getRestaurant(id: Long): Single<Restaurant>
}