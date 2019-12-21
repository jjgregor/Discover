package com.jmoney.domain.repository

import io.reactivex.Observable
import io.reactivex.Single

interface TokenRepository {

    fun getToken(
        email: String,
        password: String
    ): Single<Boolean>

    fun retrieveToken(): Observable<String>
}