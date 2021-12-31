package com.example.testapp.ui.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.common.adapter.BaseRecyclerViewAdapter
import com.example.testapp.databinding.ListItemPlaceDetailsBinding

class PlaceDetailsRecyclerViewAdapter : BaseRecyclerViewAdapter<Pair<String, String?>, PlaceDetailsRecyclerViewAdapter.PlaceDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemPlaceDetailsBinding.inflate(layoutInflater, parent, false)
        return PlaceDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PlaceDetailsViewHolder(private val binding: ListItemPlaceDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<String, String?>) {
            binding.titleItemDetails.text = item.first
            binding.infoItemDetails.text = item.second
        }
    }

}