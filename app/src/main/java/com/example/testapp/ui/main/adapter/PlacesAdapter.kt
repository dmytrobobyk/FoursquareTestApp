package com.example.testapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.BuildConfig
import com.example.testapp.R
import com.example.testapp.common.util.calculationByDistance
import com.example.testapp.databinding.ListItemPlaceBinding
import com.example.testapp.models.Result
import com.google.android.gms.maps.model.LatLng

class PlacesAdapter(private val listener: (Result) -> Unit) :
    ListAdapter<Result, PlacesAdapter.PlacesViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlaceBinding.inflate(layoutInflater, parent, false)
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PlacesViewHolder(private val binding: ListItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val IMAGE_SIZE = 88

        fun bind(place: Result, listener: (Result) -> Unit) {
            binding.placeName.text = place.name
            binding.distanceToPlace.text = calculationByDistance(LatLng(place.geocodes.main.latitude, place.geocodes.main.longitude), LatLng(BuildConfig.SEATTLE_LATTITUDE.toDouble(), BuildConfig.SEATTLE_LONGITUDE.toDouble())).toString()


            if (place.categories.isNotEmpty()) {
                binding.placeCategory.text = String.format(binding.root.context.getString(R.string.category_is), place.categories[0].name)
                Glide.with(binding.root.context)
                    .load("${place.categories[0].icon.prefix}${IMAGE_SIZE}${place.categories[0].icon.suffix}")
                    .into(binding.placeImage)
            }

            //TODO check if the item was saved into preferences
//            if (isFavorite) {
//                binding.favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
//            } else {
//                binding.favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//            }

            itemView.setOnClickListener { listener(place) }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(p0: Result, p1: Result): Boolean =
                p0.fsq_id == p1.fsq_id

            override fun areContentsTheSame(p0: Result, p1: Result): Boolean =
                p0 == p1
        }
    }
}