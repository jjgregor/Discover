package com.jmoney.data.adapter

import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.doordashapi.ApiRestaurant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

private const val ID = 100L
private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val IMAGE_URL = "image_url"
private const val STATUS = "status"

class RestaurantAdapterTest {

    private val adapter = RestaurantAdapter()

    @Test
    fun givenApiRestaurant_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )

        // When
        val result = adapter(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )
        assertThat(expected).isEqualToComparingFieldByField(result)
    }

    @Test
    fun givenApiRestaurant_isMissingID_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adapter(apiRestaurant)
        }.withMessage("ID must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingName_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adapter(apiRestaurant)
        }.withMessage("Name must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingDescription_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            imageUrl = IMAGE_URL,
            status = STATUS
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adapter(apiRestaurant)
        }.withMessage("Description must not be null")
    }

    @Test
    fun givenApiRestaurant_isMissingImageUrl_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            description = DESCRIPTION,
            status = STATUS
        )

        // When
        val result = adapter(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = "",
            status = STATUS
        )
        assertThat(expected).isEqualToComparingFieldByField(result)

    }

    @Test
    fun givenApiRestaurant_isMissingStatus_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL
        )

        // Then
        assertThatExceptionOfType(IllegalStateException::class.java).isThrownBy {
            adapter(apiRestaurant)
        }.withMessage("status must not be null")
    }
}