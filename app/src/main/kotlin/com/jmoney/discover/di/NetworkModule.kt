package com.jmoney.discover.di

import android.app.Application
import com.jmoney.doordashapi.DoorDashApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIME_OUT = 30L

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideDoorDashApiBuilder(
        retrofitBuilder: Retrofit.Builder
    ): DoorDashApi.Builder {
        return DoorDashApi.Builder(retrofitBuilder)
    }

    @Provides
    @Singleton
    fun provideDoorDashApi(
        doorDashApiBuilder: DoorDashApi.Builder,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ) : DoorDashApi {
        return doorDashApiBuilder
            .moshi(moshi)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun provideRetroFitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }
}