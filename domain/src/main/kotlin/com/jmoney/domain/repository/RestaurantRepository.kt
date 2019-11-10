package com.jmoney.domain.repository

import com.jmoney.domain.datamodel.Restaurant
import io.reactivex.Single

interface RestaurantRepository {

    fun getRestaurants(latitude: Double, longitude: Double): Single<List<Restaurant>>
    fun getRestaurant(id: Long): Single<Restaurant>
}