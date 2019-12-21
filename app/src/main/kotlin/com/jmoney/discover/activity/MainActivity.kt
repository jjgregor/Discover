package com.jmoney.discover.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jmoney.discover.R
import com.jmoney.discover.di.AppComponentProvider
import com.jmoney.discover.fragment.LoginFragment
import com.jmoney.discover.fragment.RestaurantListFragment
import com.jmoney.discover.viewmodel.MainActivityState
import com.jmoney.discover.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as AppComponentProvider)
            .provideAppComponent()
            .inject(this)
        setContentView(R.layout.activity_main)

        viewModel.state.observe(this, Observer { navigateToFragment(it) })
    }

    private fun navigateToFragment(state: MainActivityState) {
        when (state) {
            is MainActivityState.Login -> grossFragmentSwap(LoginFragment())
            is MainActivityState.Restaurant -> grossFragmentSwap(RestaurantListFragment())
            is MainActivityState.Error -> showErrorToast()
        }
    }

    private fun showErrorToast() {
        Toast.makeText(this, "Login Failure, please try again", Toast.LENGTH_LONG).show()
    }

    fun grossFragmentSwap(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
