package com.jmoney.discover.di

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jmoney.data.repository.LikedRestaurantsDataRepository
import com.jmoney.data.repository.RestaurantDataRepository
import com.jmoney.data.repository.TokenDataRepository
import com.jmoney.discover.common.AndroidSchedulers
import com.jmoney.discover.interfaces.Schedulers
import com.jmoney.domain.repository.LikedRestaurantsRepository
import com.jmoney.domain.repository.RestaurantRepository
import com.jmoney.domain.repository.TokenRepository
import com.jmoney.doordashapi.DoorDashApi
import com.jmoney.doordashapi.DoorDashService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.Declarations::class])
class AppModule(private val application: Application) {

    @Module
    interface Declarations {

        @Binds
        fun bindRestaurantRepository(restaurantDataRepository: RestaurantDataRepository): RestaurantRepository

        @Binds
        fun bindDoorDashApi(doorDashApi: DoorDashApi): DoorDashService

        @Binds
        fun bindLikedRestaurantsRepository(
            likedRestaurantsDataRepository: LikedRestaurantsDataRepository
        ): LikedRestaurantsRepository

        @Binds
        fun bindTokenRepository(
            tokenDataRepository: TokenDataRepository
        ): TokenRepository
    }

    @Provides
    fun provideSchedulers(): Schedulers {
        return AndroidSchedulers
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun providesApplication() = application
}