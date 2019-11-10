package com.jmoney.discover.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmoney.discover.R
import com.jmoney.discover.databinding.RestaurantItemBinding
import com.jmoney.domain.datamodel.Restaurant

class RestaurantAdapter(
    val restaurants: MutableList<Restaurant> = mutableListOf()
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindRestaurant(restaurants[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.restaurant_item,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(private val binding: RestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindRestaurant(restaurant: Restaurant) {
            with(binding) {
                restaurantItemTitle.text = restaurant.name
                restaurantItemSubtitle.text = restaurant.description
                restaurantItemStatus.text = restaurant.status
                Glide.with(itemView.context)
                    .load(restaurant.imageUrl)
                    .into(restaurantItemImage)
            }
        }
    }
}