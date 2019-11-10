package com.jmoney.discover.di

import android.app.Application
import com.jmoney.data.repository.RestaurantDataRepository
import com.jmoney.discover.common.AndroidSchedulers
import com.jmoney.discover.interfaces.Schedulers
import com.jmoney.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (includes = [AppModule.Declarations::class])
class AppModule(private val application: Application) {

    @Module
    interface Declarations {

        @Binds
        fun bindRestaurantRepository(restaurantDataRepository: RestaurantDataRepository): RestaurantRepository
    }

    @Provides
    fun provideSchedulers(): Schedulers {
        return AndroidSchedulers
    }

    @Provides
    @Singleton
    internal fun providesApplication() = application
}