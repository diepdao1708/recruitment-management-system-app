package com.android.recruitment.features.onboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.R
import com.android.recruitment.databinding.ItemOnboardBinding
import com.bumptech.glide.Glide

class OnboardAdapter(
    private val intros: List<OnboardData> = emptyList(),
) : RecyclerView.Adapter<OnboardAdapter.OnboardViewHolder>() {

    inner class OnboardViewHolder(val binding: ItemOnboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboardData: OnboardData) {
            binding.tvTitle.text = onboardData.title
            binding.tvContent.text = onboardData.content
            Glide.with(binding.root)
                .load(onboardData.imageRes)
                .placeholder(R.drawable.intro_1)
                .into(binding.imgCenter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardViewHolder {
        val binding = ItemOnboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardViewHolder(binding)
    }

    override fun getItemCount() = intros.size

    override fun onBindViewHolder(holder: OnboardViewHolder, position: Int) {
        holder.bind(intros[position])
    }
}
