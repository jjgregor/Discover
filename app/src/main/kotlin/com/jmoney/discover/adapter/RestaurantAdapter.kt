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
    val restaurants: MutableList<Restaurant> = mutableListOf(),
    val onItemClick: (Long) -> Unit
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

        private val unLikedDrawable = itemView.context.getDrawable(R.drawable.ic_unliked)
        private val likedDrawable = itemView.context.getDrawable(R.drawable.ic_liked)

        fun bindRestaurant(restaurant: Restaurant) {
            with(binding) {
                restaurantItemTitle.text = restaurant.name
                restaurantItemSubtitle.text = restaurant.description
                restaurantItemStatus.text = restaurant.status
                Glide.with(itemView.context)
                    .load(restaurant.imageUrl)
                    .fitCenter()
                    .error(R.drawable.ic_android_black_24dp)
                    .into(restaurantItemImage)
                if (restaurant.isLiked) {
                    restaurantItemLikedImage.setImageDrawable(likedDrawable)
                } else {
                    restaurantItemLikedImage.setImageDrawable(unLikedDrawable)
                }

                itemView.setOnClickListener {
                    onItemClick(restaurant.id)
                    if (restaurant.isLiked) {
                        restaurantItemLikedImage.setImageDrawable(unLikedDrawable)
                    } else {
                        restaurantItemLikedImage.setImageDrawable(likedDrawable)
                    }
                    restaurant.isLiked = restaurant.isLiked.not()
                }
            }
        }
    }

}
