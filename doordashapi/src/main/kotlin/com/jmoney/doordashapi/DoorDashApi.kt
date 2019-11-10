package com.jmoney.doordashapi

import io.reactivex.Single
import javax.inject.Inject

class DoorDashApi @Inject constructor(
    private val doorDashService: DoorDashService
) : DoorDashService {

    override fun getRestaurantFromCoordinates(
        latitude: Double,
        longitude: Double,
        limit: Int,
        offset: Int
    ): Single<List<ApiRestaurant>> {
        return doorDashService.getRestaurantFromCoordinates(
            latitude = latitude,
            longitude = longitude,
            limit = limit,
            offset = offset
        )
    }

    override fun getRestaurantFromId(id: Long): Single<ApiRestaurant> {
        return doorDashService.getRestaurantFromId(id)
    }

}