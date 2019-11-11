package com.jmoney.discover.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jmoney.discover.common.TestSchedulers
import com.jmoney.discover.datamodel.RestaurantListState
import com.jmoney.domain.repository.RestaurantRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestaurantListViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private val stateObserver = mock<Observer<RestaurantListState>>()
    private val restaurantRepository = mock<RestaurantRepository>()
    private val schedulers = TestSchedulers()
    private val viewModel = RestaurantListViewModel(
        restaurantRepository = restaurantRepository,
        schedulers = schedulers
    )

    @Before
    fun setup() {
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun givenGetRestaurants_shouldPostRestaurants() {
        // Given
        given(restaurantRepository.getRestaurants(any(), any(), any(), any()))
            .willReturn(Single.just(emptyList()))

        // When
        viewModel.getRestaurantsFromCoordinates()

        // Then
        assertThat(viewModel.state.value).isEqualTo(RestaurantListState.Restaurants(emptyList()))
    }

    @Test
    fun givenGetRestaurants_returnsError_shouldPostValueError() {
        // Given
        given(restaurantRepository.getRestaurants(any(), any(), any(), any()))
            .willReturn(Single.error(Throwable()))

        // When
        viewModel.getRestaurantsFromCoordinates()

        // Then
        assertThat(viewModel.state.value).isEqualTo(RestaurantListState.Error)
    }

    @Test
    fun givenShouldDownLoadNextPage_shouldNot_nothingHappens() {
        // When
        viewModel.shouldDownLoadNextPage(
            totalCount = 10,
            firstVisible = 1,
            visibleCount = 6
        )

        // Then
        then(restaurantRepository).shouldHaveZeroInteractions()
    }

    @Test
    fun givenShouldDownLoadNextPage_shouldGetRestaurants() {
        // Given
        given(restaurantRepository.getRestaurants(any(), any(), any(), any()))
            .willReturn(Single.just(emptyList()))

        // When
        viewModel.shouldDownLoadNextPage(
            totalCount = 10,
            firstVisible = 9,
            visibleCount = 1
        )

        // Then
        then(restaurantRepository).should(atLeastOnce())
    }

}