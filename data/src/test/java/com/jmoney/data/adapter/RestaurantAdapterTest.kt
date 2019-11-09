package com.jmoney.data.adapter

import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.doordashapi.ApiRestaurant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

private const val ID = 100L
private const val NAME = "name"
private const val IMAGE_URL = "image_url"
private const val STATUS = "status"
private const val DELIVERY_FEE = "delivery_fee"

class RestaurantAdapterTest {

    private val adpater = RestaurantAdapter()

    @Test
    fun givenApiRestaurant_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            id = ID,
            name = NAME,
            imageUrl = IMAGE_URL,
            status = STATUS,
            deliveryFee = DELIVERY_FEE
        )

        // When
        val result = adpater(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            imageUrl = IMAGE_URL,
            status = STATUS,
            deliveryFee = DELIVERY_FEE
        )
        assertThat(expected).isEqualToComparingFieldByField(result)
    }

    @Test
    fun givenApiRestaurant_isMissingID_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            name = NAME,
            imageUrl = IMAGE_URL,
            status = STATUS,
            deliveryFee = DELIVERY_FEE
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adpater(apiRestaurant)
        }.withMessage("ID must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingName_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            id = ID,
            imageUrl = IMAGE_URL,
            status = STATUS,
            deliveryFee = DELIVERY_FEE
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adpater(apiRestaurant)
        }.withMessage("Name must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingImageUrl_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            id = ID,
            name = NAME,
            status = STATUS,
            deliveryFee = DELIVERY_FEE
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adpater(apiRestaurant)
        }.withMessage("imageUrl must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingStatus_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            id = ID,
            name = NAME,
            imageUrl = IMAGE_URL,
            deliveryFee = DELIVERY_FEE
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adpater(apiRestaurant)
        }.withMessage("status must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingDeliveryFee_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            id = ID,
            name = NAME,
            imageUrl = IMAGE_URL,
            status = STATUS
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adpater(apiRestaurant)
        }.withMessage("deliveryFee must not be null")
    }
}