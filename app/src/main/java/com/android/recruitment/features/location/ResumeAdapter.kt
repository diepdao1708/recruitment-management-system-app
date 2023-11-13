package com.android.recruitment.features.location

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemResumeBinding

class ResumeAdapter(
    private var resumeList: List<ItemResume> = emptyList(),
    private val listener: OnClickListener
) : RecyclerView.Adapter<ResumeAdapter.ResumeViewHolder>() {

    inner class ResumeViewHolder(private val binding: ItemResumeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(resume: ItemResume) {
            binding.tvNameResume.text = resume.name

            binding.root.setOnClickListener {
                listener.onItemClick(resume)
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(resume: ItemResume)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeViewHolder {
        val binding = ItemResumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResumeViewHolder(binding)
    }

    override fun getItemCount() = resumeList.size

    override fun onBindViewHolder(holder: ResumeViewHolder, position: Int) {
        holder.bind(resumeList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(resumeList: List<ItemResume>) {
        this.resumeList = resumeList
        notifyDataSetChanged()
    }
}