package com.android.recruitment.features.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemLocationBinding

class LocationAdapter(
    private val listener: OnClickListener,
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.root.setOnClickListener { listener.onItemClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind()
    }

    interface OnClickListener {
        fun onItemClick()
    }
}