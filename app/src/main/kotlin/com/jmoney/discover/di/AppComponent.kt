package com.jmoney.discover.di

import com.jmoney.discover.activity.MainActivity
import com.jmoney.discover.fragment.RestaurantListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(restaurantListFragment: RestaurantListFragment)
    fun inject(restaurantListFragment: MainActivity)
}

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}