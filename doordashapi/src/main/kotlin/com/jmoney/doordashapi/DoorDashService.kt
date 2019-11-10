package com.jmoney.doordashapi

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DoorDashService {

    @GET("/v2/restaurant/")
    fun getRestaurantFromCoordinates(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<List<ApiRestaurant>>

    @GET("/v2/restaurant/{restaurant_id}/")
    fun getRestaurantFromId(
        @Path("restaurant_id") id: Long
    ): Single<ApiRestaurant>

}