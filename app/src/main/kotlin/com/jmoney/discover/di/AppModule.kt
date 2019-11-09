package com.jmoney.discover.di

import android.app.Application
import com.jmoney.discover.common.AndroidSchedulers
import com.jmoney.discover.interfaces.Schedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (includes = [AppModule.Declarations::class])
class AppModule(private val application: Application) {

    @Module
    interface Declarations {

    }

    @Provides
    fun provideSchedulers(): Schedulers {
        return AndroidSchedulers
    }

    @Provides
    @Singleton
    internal fun providesApplication() = application
}