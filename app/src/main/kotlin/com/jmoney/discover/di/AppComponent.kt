package com.jmoney.discover.di

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

}

interface AppComponentProvider {
    fun provideAppComponent(): AppComponent
}