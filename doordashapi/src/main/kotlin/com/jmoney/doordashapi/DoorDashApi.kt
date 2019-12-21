package com.jmoney.doordashapi

import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class DoorDashApi @Inject constructor(
    private val doorDashService: DoorDashService
) : DoorDashService {

    class Builder(
        private val retrofitBuilder: Retrofit.Builder
    ) {
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var moshi: Moshi

        fun okHttpClient(client: OkHttpClient): Builder {
            okHttpClient = client
            return this
        }

        fun moshi(moshi: Moshi): Builder {
            this.moshi = moshi
            retrofitBuilder.addConverterFactory(MoshiConverterFactory.create(moshi))
            return this
        }

        fun build(): DoorDashApi {
            val retrofitBuilder = retrofitBuilder
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

            val doorDashService = retrofitBuilder.create(DoorDashService::class.java)
            return DoorDashApi(
                doorDashService = doorDashService
            )
        }
    }

    override fun getRestaurantFromCoordinates(
        latitude: Double,
        longitude: Double,
        limit: Int,
        offset: Int
    ): Single<List<ApiRestaurant>> {
        return doorDashService.getRestaurantFromCoordinates(
            latitude = latitude,
            longitude = longitude,
            limit = limit,
            offset = offset
        )
    }

    override fun getRestaurantFromId(id: Long): Single<ApiRestaurant> {
        return doorDashService.getRestaurantFromId(id)
    }

    override fun postAuthToken(tokenRequest: TokenRequest): Single<TokenResponse> {
        return doorDashService.postAuthToken(tokenRequest)
    }
}