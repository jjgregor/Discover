package com.jmoney.discover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(viewModelClass: Class<T>) : T {
        return requireNotNull(viewModelMap[viewModelClass]) {
            "Unknown ViewModel class $viewModelClass"
        }.get() as T
    }
}