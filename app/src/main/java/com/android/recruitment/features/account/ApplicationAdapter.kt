package com.android.recruitment.features.account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemApplicationBinding

class ApplicationAdapter(
    private var applications: List<ApplicationUi> = emptyList(),
    private val listener: OnClickListener,
) : RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>() {

    inner class ApplicationViewHolder(private val binding: ItemApplicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(applicationUi: ApplicationUi) {
            binding.apply {
                tvNameJob.text = applicationUi.name
                tvSalaryJob.text = applicationUi.salary
                tvTerminationDate.text = applicationUi.terminationDate
            }

            binding.tvCv.setOnClickListener {
                listener.onItemClick(applicationUi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding =
            ItemApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicationViewHolder(binding)
    }

    override fun getItemCount() = applications.size

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        holder.bind(applications[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(applications: List<ApplicationUi>) {
        this.applications = applications
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onItemClick(applicationUi: ApplicationUi)
    }
}