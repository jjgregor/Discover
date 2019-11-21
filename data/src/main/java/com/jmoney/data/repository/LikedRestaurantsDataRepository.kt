package com.jmoney.data.repository

import android.content.SharedPreferences
import com.jmoney.domain.repository.LikedRestaurantsRepository
import javax.inject.Inject

class LikedRestaurantsDataRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences

) : LikedRestaurantsRepository {

    override fun setLikedRestaurant(id: Long) {
        val restaurantId = id.toString()
        val isLiked = sharedPreferences.getBoolean(restaurantId, false)
        if (isLiked) {
            sharedPreferences.edit().remove(restaurantId).apply()
        } else {
            sharedPreferences.edit().putBoolean(restaurantId, true).apply()
        }
    }

    override fun getIsRestaurantLiked(restaurantId: Long): Boolean {
        return sharedPreferences.getBoolean(restaurantId.toString(), false)
    }
}
