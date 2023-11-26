package com.android.recruitment.features.user_follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemLocationBinding
import com.android.recruitment.databinding.ItemUserBinding

class UserFollowAdapter(
    private val listener: OnClickListener,
) : RecyclerView.Adapter<UserFollowAdapter.UserFollowViewHolder>() {

    inner class UserFollowViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.root.setOnClickListener { listener.onItemClick() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFollowViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFollowViewHolder(binding)
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: UserFollowViewHolder, position: Int) {
        holder.onBind()
    }

    interface OnClickListener {
        fun onItemClick()
    }
}