package com.jmoney.data.repository

import com.jmoney.data.adapter.RestaurantAdapter
import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.domain.repository.RestaurantRepository
import com.jmoney.doordashapi.DoorDashService
import io.reactivex.Single
import javax.inject.Inject

class RestaurantDataRepository @Inject constructor(
    private val doorDashService: DoorDashService,
    private val restaurantAdapter: RestaurantAdapter
) : RestaurantRepository {

    override fun getRestaurant(id: Long) : Single<Restaurant> {
        return doorDashService.getRestaurantFromId(id).map { restaurantAdapter(it) }
    }

    override fun getRestaurants(
        latitude: Double,
        longitude: Double,
        limit: Int,
        offset: Int
    ): Single<List<Restaurant>> {
        return doorDashService.getRestaurantFromCoordinates(
            latitude = latitude,
            longitude = longitude,
            limit = limit,
            offset = offset
        )
            .map { restaurants -> restaurants.map { restaurantAdapter(it) } }
    }
}