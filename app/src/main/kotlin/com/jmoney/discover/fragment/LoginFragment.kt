package com.jmoney.discover.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jmoney.discover.R
import com.jmoney.discover.activity.MainActivity
import com.jmoney.discover.databinding.FragmentLoginBinding
import com.jmoney.discover.di.AppComponentProvider
import com.jmoney.discover.viewmodel.LoginViewModel
import com.jmoney.discover.viewmodel.LoginViewState
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as AppComponentProvider)
            .provideAppComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { handleState(it) })
        with(binding) {
            submitButton.setOnClickListener {
                viewModel.getToken(
                    email = loginEmail.text.toString(),
                    password = loginPassword.text.toString()
                )
            }
        }
    }

    private fun handleState(state: LoginViewState) {
        when (state) {
            is LoginViewState.Loading -> setLoading()
            is LoginViewState.Failure -> setFailureState()
            is LoginViewState.Success -> setSuccessState()
        }
    }

    private fun setSuccessState() {
        binding.loginProgressbar.visibility = View.GONE
        (requireActivity() as MainActivity).grossFragmentSwap(RestaurantListFragment())
    }

    private fun setFailureState() {
        binding.loginProgressbar.visibility = View.GONE
        Snackbar.make(requireView(), "Login Failure, please try again", Snackbar.LENGTH_LONG)
    }

    private fun setLoading() {
        binding.loginProgressbar.visibility = View.VISIBLE
    }

}