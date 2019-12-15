package com.jmoney.domain.repository

interface LikedRestaurantsRepository {

    fun setLikedRestaurant(id: Long)

    fun getIsRestaurantLiked(restaurantId: Long) : Boolean
}