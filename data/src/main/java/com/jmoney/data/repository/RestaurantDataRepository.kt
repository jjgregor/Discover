package com.jmoney.data.repository

import com.jmoney.data.adapter.RestaurantAdapter
import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.domain.repository.RestaurantRepository
import com.jmoney.doordashapi.DoorDashApi
import io.reactivex.Single
import javax.inject.Inject

class RestaurantDataRepository @Inject constructor(
    private val doorDashApi: DoorDashApi,
    private val restaurantAdapter: RestaurantAdapter
) : RestaurantRepository {

    override fun getRestaurant(id: Long) : Single<Restaurant> {
        return doorDashApi.getRestaurantFromId(id).map { restaurantAdapter(it) }
    }

    override fun getRestaurants(
        latitude: Double,
        longitude: Double,
        limit: Int,
        offset: Int
    ): Single<List<Restaurant>> {
        return doorDashApi.getRestaurantFromCoordinates(
            latitude = latitude,
            longitude = longitude,
            limit = limit,
            offset = offset
        )
            .map { restaurants -> restaurants.map { restaurantAdapter(it) } }
    }
}