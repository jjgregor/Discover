package com.jmoney.discover.datamodel

import com.jmoney.domain.datamodel.Restaurant

sealed class RestaurantListState {

    data class Restaurants(val restaurant: List<Restaurant>) : RestaurantListState()

    object Loading : RestaurantListState()

    object Error : RestaurantListState()
}