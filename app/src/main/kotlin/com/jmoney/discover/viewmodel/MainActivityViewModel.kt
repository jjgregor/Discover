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

class MainActivityViewModel  @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val schedulers: Schedulers
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<MainActivityState>()
    val state: LiveData<MainActivityState> = _state

    init {
        getStartingFragment()
    }

    private fun getStartingFragment() {
        tokenRepository
            .retrieveToken()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.mainThread())
            .subscribeBy(
                onNext = { token ->
                    if (token.isEmpty()) {
                        _state.postValue(MainActivityState.Login)
                    } else {
                        _state.postValue(MainActivityState.Restaurant)
                    }
                },
                onError = {
                    Timber.e(it, "Error getting token")
                    _state.postValue(MainActivityState.Error)
                }
            ).addTo(compositeDisposable)
    }
}

sealed class MainActivityState {

    object Login: MainActivityState()

    object Restaurant: MainActivityState()

    object Error: MainActivityState()
}