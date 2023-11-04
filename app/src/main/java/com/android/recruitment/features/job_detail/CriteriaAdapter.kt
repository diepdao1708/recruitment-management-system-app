package com.android.recruitment.features.job_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recruitment.databinding.ItemCriteriaBinding
import com.android.recruitment.features.home.CriteriaUi

class CriteriaAdapter(
    private var criteriaUiList: List<CriteriaUi> = emptyList(),
) : RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder>() {
    inner class CriteriaViewHolder(private val binding: ItemCriteriaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(criteriaUi: CriteriaUi) {
            binding.tvTitle.text = criteriaUi.title
            binding.tvQualification.text = criteriaUi.qualification
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CriteriaViewHolder {
        val binding =
            ItemCriteriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CriteriaViewHolder(binding)
    }

    override fun getItemCount() = criteriaUiList.size

    override fun onBindViewHolder(holder: CriteriaViewHolder, position: Int) {
        holder.bind(criteriaUiList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(criteriaUiList: List<CriteriaUi>) {
        this.criteriaUiList = criteriaUiList
        notifyDataSetChanged()
    }
}