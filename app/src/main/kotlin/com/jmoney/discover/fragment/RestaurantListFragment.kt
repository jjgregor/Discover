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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmoney.discover.R
import com.jmoney.discover.adapter.RestaurantAdapter
import com.jmoney.discover.databinding.FragmentRestaurantListBinding
import com.jmoney.discover.datamodel.RestaurantListState
import com.jmoney.discover.di.AppComponentProvider
import com.jmoney.discover.viewmodel.RestaurantListViewModel
import javax.inject.Inject

class RestaurantListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<RestaurantListViewModel> { viewModelFactory }
    private lateinit var binding: FragmentRestaurantListBinding
    private lateinit var recyclerViewAdapter: RestaurantAdapter

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
            R.layout.fragment_restaurant_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewAdapter = RestaurantAdapter(
            onItemClick = { restaurantId ->
                viewModel.setRestaurantLiked(restaurantId)
            }
        )

        with(binding) {
            fragmentRestaurantListRefresh.setOnRefreshListener { viewModel.getRestaurantsFromCoordinates() }
            fragmentRestaurantListRecycler.apply {
                this.adapter = recyclerViewAdapter
                this.layoutManager = layoutManager
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        viewModel.shouldDownLoadNextPage(
                            totalCount = layoutManager.itemCount,
                            firstVisible = layoutManager.findFirstVisibleItemPosition(),
                            visibleCount = layoutManager.childCount
                        )
                    }
                })
            }
        }

        viewModel.state.observe(this, Observer { updateFragment(it) })
            .apply { viewModel.getRestaurantsFromCoordinates() }
    }

    private fun updateFragment(state: RestaurantListState) {
        when (state) {
            is RestaurantListState.Error -> setErrorState()
            is RestaurantListState.Loading -> setLoadingState()
            is RestaurantListState.Restaurants -> updateRestaurantList(state)
        }
    }

    private fun updateRestaurantList(state: RestaurantListState.Restaurants) {
        with(binding) {
            if (fragmentRestaurantListRecycler.visibility != View.VISIBLE) {
                fragmentRestaurantListRecycler.visibility = View.VISIBLE
            }
            if (fragmentRestaurantListEmpty.visibility != View.GONE) {
                fragmentRestaurantListEmpty.visibility = View.GONE
            }
            if (fragmentRestaurantListRefresh.isRefreshing) {
                fragmentRestaurantListRefresh.isRefreshing = false
            }

            recyclerViewAdapter.apply {
                restaurants.addAll(state.restaurant)
                notifyDataSetChanged()
            }
        }
    }

    private fun setLoadingState() {
        binding.fragmentRestaurantListRefresh.isRefreshing = true
    }

    private fun setErrorState() {
        with(binding) {
            fragmentRestaurantListRecycler.visibility = View.GONE
            fragmentRestaurantListRefresh.isRefreshing = false
            fragmentRestaurantListEmpty.visibility = View.VISIBLE
        }
    }

}