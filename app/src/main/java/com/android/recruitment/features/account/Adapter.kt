package com.android.recruitment.features.account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.R
import com.android.recruitment.databinding.ItemBinding

enum class Type {
    EXPERIENCE, MAJOR,
}

class Adapter(
    private var list: List<String> = emptyList(),
    private val listener: OnClickListener,
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var itemSelected: String = ""
    private var type: Type? = Type.EXPERIENCE

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tv.text = item
            if (item == itemSelected) {
                binding.tv.setBackgroundResource(R.color.background)
            } else {
                binding.tv.setBackgroundResource(R.color.white)
            }

            binding.root.setOnClickListener {
                listener.onItemClick(item, type)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<String>, item: String, type: Type?) {
        this.list = list
        this.itemSelected = item
        this.type = type
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(item: String, type: Type?)
    }
}