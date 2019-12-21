package com.jmoney.data.repository

import android.content.SharedPreferences
import com.jmoney.domain.repository.TokenRepository
import com.jmoney.doordashapi.DoorDashService
import com.jmoney.doordashapi.TokenRequest
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

private const val TOKEN = "token"

class TokenDataRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val doorDashService: DoorDashService
) : TokenRepository {

    override fun retrieveToken(): Observable<String> {
        return Observable.just(sharedPreferences.getString(TOKEN, ""))
    }

    override fun getToken(
        email: String,
        password: String
    ): Single<Boolean> {
        return doorDashService.postAuthToken(
            tokenRequest = TokenRequest(
                email = email,
                password = password
            )
        )
            .map {
                if (it.token.isNotEmpty()) {
                    sharedPreferences.edit().putString(TOKEN, it.token).apply()
                    true
                } else {
                    false
                }
            }
    }
}