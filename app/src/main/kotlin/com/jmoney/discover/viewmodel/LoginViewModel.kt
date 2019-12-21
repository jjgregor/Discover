package com.jmoney.discover.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmoney.discover.interfaces.Schedulers
import com.jmoney.domain.repository.TokenRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val schedulers: Schedulers
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<LoginViewState>()
    val state: LiveData<LoginViewState> = _state

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getToken(
        email: String,
        password: String
    ) {
        _state.postValue(LoginViewState.Loading)
        tokenRepository.getToken(
            email = email,
            password = password
        )
            .observeOn(schedulers.mainThread())
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onSuccess = { hasToken ->
                    if (hasToken) {
                        _state.postValue(LoginViewState.Success)
                    } else {
                        _state.postValue(LoginViewState.Failure)
                    }
                },
                onError = {
                    Timber.e(it, "Error Getting Token")
                    _state.postValue(LoginViewState.Failure)
                }
            ).addTo(compositeDisposable)
    }
}

sealed class LoginViewState {

    object Success : LoginViewState()

    object Loading : LoginViewState()

    object Failure : LoginViewState()
}