package com.jmoney.data.adapter

import com.jmoney.domain.datamodel.Restaurant
import com.jmoney.domain.repository.LikedRestaurantsRepository
import com.jmoney.doordashapi.ApiRestaurant
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

private const val ID = 100L
private const val NAME = "name"
private const val DESCRIPTION = "description"
private const val IMAGE_URL = "image_url"
private const val STATUS = "status"
private const val RANDOM_DOGGO = "https://dog.ceo/api/breeds/image/random"

class RestaurantAdapterTest {

    private val likedRestaurantsRepository = mock<LikedRestaurantsRepository>()
    private val adapter = RestaurantAdapter(
        likedRestaurantsRepository = likedRestaurantsRepository
    )

    @Test
    fun givenApiRestaurant_isLiked_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )
        given(likedRestaurantsRepository.getIsRestaurantLiked(ID)).willReturn(true)

        // When
        val result = adapter(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS,
            isLiked = true
        )
        assertThat(expected).isEqualToComparingFieldByField(result)
    }

    @Test
    fun givenApiRestaurant_isNotLiked_shouldReturnRestaurant() {
        // Given
        val apiRestaurant = ApiRestaurant(
            restaurantId = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS
        )
        given(likedRestaurantsRepository.getIsRestaurantLiked(ID)).willReturn(false)

        // When
        val result = adapter(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = IMAGE_URL,
            status = STATUS,
            isLiked = false
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
        given(likedRestaurantsRepository.getIsRestaurantLiked(ID)).willReturn(true)

        // When
        val result = adapter(apiRestaurant)

        // Then
        val expected = Restaurant(
            id = ID,
            name = NAME,
            description = DESCRIPTION,
            imageUrl = RANDOM_DOGGO,
            status = STATUS,
            isLiked = true
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