package com.jmoney.domain.datamodel

data class Restaurant(
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val status: String,
    val deliveryFee: String
)