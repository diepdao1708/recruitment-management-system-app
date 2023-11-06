package com.android.recruitment.features.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemNewsBinding

class NewsAdapter(
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jobUi: JobUi) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(jobs: List<JobUi>) {

        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(jobUi: JobUi)
    }
}