package com.jmoney.doordashapi

import com.jmoney.domain.datamodel.Restaurant
import io.reactivex.Single
import javax.inject.Inject

class DoorDashApi @Inject constructor(
    private val doorDashService: DoorDashService
) : DoorDashService {

    override fun getRestaurantFromCoordinates(
        lat: Long,
        lng: Long
    ): Single<List<ApiRestaurant>> {
        return doorDashService.getRestaurantFromCoordinates(lat, lng)
    }

    override fun getRestaurantFromId(id: Long): Single<ApiRestaurant> {
        return doorDashService.getRestaurantFromId(id)
    }

}