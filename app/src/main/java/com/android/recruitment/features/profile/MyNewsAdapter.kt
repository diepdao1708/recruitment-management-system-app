package com.android.recruitment.features.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemMyNewsBinding

class MyNewsAdapter(
    private val listener: OnClickListener,
) : RecyclerView.Adapter<MyNewsAdapter.MyNewsViewHolder>() {

    inner class MyNewsViewHolder(private val binding: ItemMyNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.llFavorite.setOnClickListener { listener.onFavorite() }
            binding.tvDelete.setOnClickListener { listener.onDelete() }
            binding.tvEdit.setOnClickListener { listener.onEdit() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsViewHolder {
        val binding = ItemMyNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyNewsViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: MyNewsViewHolder, position: Int) {
        holder.onBind()
    }

    interface OnClickListener {
        fun onDelete()
        fun onFavorite()
        fun onEdit()
    }
}