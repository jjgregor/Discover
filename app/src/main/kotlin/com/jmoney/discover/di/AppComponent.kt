package com.jmoney.discover.di

import android.content.SharedPreferences
import com.jmoney.discover.activity.MainActivity
import com.jmoney.discover.fragment.LoginFragment
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

    fun inject(restaurantActivity: MainActivity)

    fun inject(loginFragment: LoginFragment)

    fun sharedPreferences(): SharedPreferences
}

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}