package com.android.recruitment.features.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemJobBinding

class JobAdapter(
    private var jobs: List<JobUi> = emptyList(),
    private val listener: OnClickListener,
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    inner class JobViewHolder(private val binding: ItemJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jobUi: JobUi) {
            binding.apply {
                tvNameJob.text = jobUi.name
                tvSalaryJob.text = jobUi.salary
                tvTerminationDate.text = jobUi.terminationDate
                tvYearOfExperience.text = jobUi.yearOfExperience
            }

            binding.root.setOnClickListener {
                listener.onItemClick(jobUi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun getItemCount() = jobs.size

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(jobs: List<JobUi>) {
        this.jobs = jobs
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(jobUi: JobUi)
    }
}