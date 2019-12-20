package com.jmoney.data.adapter

import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.domain.repository.LikedRestaurantsRepository
import com.jmoney.doordashapi.ApiRestaurant
import javax.inject.Inject

private const val RANDOM_DOGGO = "https://dog.ceo/api/breeds/image/random"

class RestaurantAdapter @Inject constructor(
    private val likedRestaurantsRepository: LikedRestaurantsRepository
) {

    operator fun invoke(apiRestaurant: ApiRestaurant) : Restaurant {
        val restaurantId = checkNotNull(apiRestaurant.restaurantId) { "ID must not be null" }
        return Restaurant(
            id = restaurantId,
            name = checkNotNull(apiRestaurant.name) { "Name must not be null" },
            description = checkNotNull(apiRestaurant.description) { "Description must not be null" },
            imageUrl = apiRestaurant.imageUrl ?: RANDOM_DOGGO,
            status = checkNotNull(apiRestaurant.status) { "status must not be null" },
            isLiked = likedRestaurantsRepository.getIsRestaurantLiked(restaurantId)
        )
    }
}