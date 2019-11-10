package com.jmoney.discover.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jmoney.discover.R
import com.jmoney.discover.databinding.FragmentRestaurantListBinding
import com.jmoney.discover.datamodel.RestaurantListState
import com.jmoney.discover.di.AppComponentProvider
import com.jmoney.discover.viewmodel.RestaurantListViewModel
import javax.inject.Inject

class RestaurantListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RestaurantListViewModel
    private lateinit var binding: FragmentRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as AppComponentProvider)
            .provideAppComponent()
            .inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[RestaurantListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentRestaurantListRefresh.setOnRefreshListener { viewModel.getRestaurantsFromCoordinates() }

        viewModel.state.observe(this, Observer { updateFragment(it) })
            .apply { viewModel.getRestaurantsFromCoordinates() }
    }

    private fun updateFragment(state: RestaurantListState) {
        when (state) {
            is RestaurantListState.Error -> setErrorState()
            is RestaurantListState.Loading -> setLoadingState()
            is RestaurantListState.Restaurants -> updateRestaurantList()
        }
    }

    private fun updateRestaurantList() {
        with(binding) {
            fragmentRestaurantListRecycler.visibility = View.VISIBLE
            fragmentRestaurantListRefresh.isRefreshing = false
            fragmentRestaurantListEmpty.visibility = View.GONE
        }
    }

    private fun setLoadingState() {
        with(binding) {
            fragmentRestaurantListRecycler.visibility = View.GONE
            fragmentRestaurantListRefresh.isRefreshing = true
            fragmentRestaurantListEmpty.visibility = View.GONE
        }
    }

    private fun setErrorState() {
        with(binding) {
            fragmentRestaurantListRecycler.visibility = View.GONE
            fragmentRestaurantListRefresh.isRefreshing = false
            fragmentRestaurantListEmpty.visibility = View.VISIBLE
        }
    }

}