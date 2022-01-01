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
import com.example.testapp.common.util.getDistanceToSeattle
import com.example.testapp.common.util.getPlaceCategory
import com.example.testapp.databinding.ListItemPlaceBinding
import com.example.testapp.ui.details.models.PlaceDetailsInfo
import com.google.android.gms.maps.model.LatLng

class PlacesAdapter(private val listener: (PlaceDetailsInfo) -> Unit) :
    ListAdapter<PlaceDetailsInfo, PlacesAdapter.PlacesViewHolder>(DIFF_CALLBACK) {


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

        fun bind(place: PlaceDetailsInfo, listener: (PlaceDetailsInfo) -> Unit) {
            binding.placeName.text = place.name
            binding.distanceToPlace.text = getDistanceToSeattle(place.latitude, place.longitude)
            binding.placeCategory.text = place.category

            Glide.with(binding.root.context)
                .load(place.imageUrl)
                .into(binding.placeImage)

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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceDetailsInfo>() {
            override fun areItemsTheSame(p0: PlaceDetailsInfo, p1: PlaceDetailsInfo): Boolean =
                p0.fsq_id == p1.fsq_id

            override fun areContentsTheSame(p0: PlaceDetailsInfo, p1: PlaceDetailsInfo): Boolean =
                p0 == p1
        }
    }
}