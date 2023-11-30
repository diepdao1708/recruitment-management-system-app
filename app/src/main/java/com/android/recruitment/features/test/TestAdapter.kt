package com.android.recruitment.features.test

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.R
import com.android.recruitment.databinding.ItemTestBinding

class TestAdapter(
    private val listener: OnClickListener,
) : ListAdapter<TestUiState, TestAdapter.TestViewHolder>(TestDiffCallBack()) {
    inner class TestViewHolder(private val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(test: TestUiState) {
            binding.tvContent.text = "${test.id}. ${test.content}"

            binding.rbTest.setOnCheckedChangeListener { _, i ->
                when (i) {
                    R.id.rb_1 -> listener.onClick(test.copy(point = 1))
                    R.id.rb_2 -> listener.onClick(test.copy(point = 2))
                    R.id.rb_3 -> listener.onClick(test.copy(point = 3))
                    R.id.rb_4 -> listener.onClick(test.copy(point = 4))
                    R.id.rb_5 -> listener.onClick(test.copy(point = 5))
                }
            }

            update(test.point)
        }

        fun update(point: Int) {
            when (point) {
                1 -> binding.rb1.isChecked = true
                2 -> binding.rb2.isChecked = true
                3 -> binding.rb3.isChecked = true
                4 -> binding.rb4.isChecked = true
                5 -> binding.rb5.isChecked = true
            }
        }
    }

    interface OnClickListener {
        fun onClick(test: TestUiState)
    }

    class TestDiffCallBack : DiffUtil.ItemCallback<TestUiState>() {
        override fun areItemsTheSame(oldItem: TestUiState, newItem: TestUiState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TestUiState, newItem: TestUiState): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: TestUiState, newItem: TestUiState): Any? {
            return if (oldItem.point != newItem.point) "update"
            else null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            ItemTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TestViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains("update")) {
            holder.update(getItem(position).point)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setIsRecyclable(false)
    }
}