package com.android.recruitment.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemNewsBinding

class NewsAdapter(
    private val listener: OnClickListener,
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvContact.setOnClickListener {
                listener.onItemClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind()
    }

    interface OnClickListener {
        fun onItemClick()
    }
}