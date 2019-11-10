package com.jmoney.discover.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jmoney.discover.viewmodel.RestaurantListViewModel
import com.jmoney.discover.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelProviderFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantListViewModel::class)
    internal abstract fun bindRestaurantListViewModel(viewModel: RestaurantListViewModel): ViewModel
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey (val value: KClass<out ViewModel>)