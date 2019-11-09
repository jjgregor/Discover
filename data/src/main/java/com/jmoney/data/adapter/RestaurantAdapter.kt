package com.jmoney.data.adapter

import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.doordashapi.ApiRestaurant
import javax.inject.Inject

class RestaurantAdapter @Inject constructor() {

    operator fun invoke(apiRestaurant: ApiRestaurant) : Restaurant {
        return Restaurant(
            id = checkNotNull(apiRestaurant.id) { "ID must not be null" },
            name = checkNotNull(apiRestaurant.name) { "Name must not be null" },
            imageUrl = checkNotNull(apiRestaurant.imageUrl) { "imageUrl must not be null" },
            status = checkNotNull(apiRestaurant.status) { "status must not be null" },
            deliveryFee = checkNotNull(apiRestaurant.deliveryFee) { "deliveryFee must not be null" }
        )
    }
}