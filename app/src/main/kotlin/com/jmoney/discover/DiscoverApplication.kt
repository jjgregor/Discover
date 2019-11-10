package com.jmoney.discover

import android.app.Application
import com.jmoney.discover.di.*

class DiscoverApplication : Application(), AppComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    override fun provideAppComponent(): AppComponent = appComponent
}