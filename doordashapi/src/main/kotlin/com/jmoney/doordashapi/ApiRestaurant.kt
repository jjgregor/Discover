package com.jmoney.doordashapi

import com.squareup.moshi.Json

data class ApiRestaurant(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "cover_img_url") val imageUrl: String? = null,
    @Json(name = "status") val status: String? = null
)