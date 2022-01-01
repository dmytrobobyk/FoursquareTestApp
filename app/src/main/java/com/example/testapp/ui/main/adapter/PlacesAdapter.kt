package com.example.testapp.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.common.util.getDistanceToSeattle
import com.example.testapp.databinding.ListItemPlaceBinding
import com.example.testapp.ui.details.models.PlaceDetailsInfo

class PlacesAdapter(private val itemClickListener: (PlaceDetailsInfo) -> Unit, private val favoriteClickListener: (PlaceDetailsInfo) -> Unit) :
    ListAdapter<PlaceDetailsInfo, PlacesAdapter.PlacesViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlaceBinding.inflate(layoutInflater, parent, false)
        return PlacesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener, favoriteClickListener)
    }

    class PlacesViewHolder(private val binding: ListItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: PlaceDetailsInfo, listener: (PlaceDetailsInfo) -> Unit, favoriteClickListener: (PlaceDetailsInfo) -> Unit) {
            binding.placeName.text = place.name
            binding.distanceToPlace.text = getDistanceToSeattle(place.latitude, place.longitude)
            binding.placeCategory.text = place.category

            Glide.with(binding.root.context)
                .load(place.imageUrl)
                .into(binding.placeImage)

            binding.favoriteImage.setImageResource(getFavoriteImage(place.isFavorite))

            binding.favoriteImage.setOnClickListener {
                favoriteClickListener(place)
            }
            itemView.setOnClickListener { listener(place) }
        }

        private fun getFavoriteImage(isFavorite: Boolean): Int {
            return if (isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlaceDetailsInfo>() {
            override fun areItemsTheSame(p0: PlaceDetailsInfo, p1: PlaceDetailsInfo): Boolean {
                Log.i("debug", " here " + p0 + " inItems " + p1)
                return p0.fsq_id == p1.fsq_id
            }

            override fun areContentsTheSame(p0: PlaceDetailsInfo, p1: PlaceDetailsInfo): Boolean {
                Log.i("debug", "here " + p0 + " inContents" + p1)
                return p0 == p1
            }
        }
    }
}