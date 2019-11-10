package com.jmoney.discover

import android.app.Application
import com.jmoney.discover.di.AppComponent
import com.jmoney.discover.di.AppModule
import com.jmoney.discover.di.DaggerAppComponent
import com.jmoney.discover.di.NetworkModule

class DiscoverApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    fun getPetMatchComponent() = appComponent
}