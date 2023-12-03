package com.android.recruitment.features.account

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.recruitment.R
import com.android.recruitment.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding
    var onClick: ((String, Type?) -> Unit)? = null
    private var itemSelected: String? = null
    private var list: List<String> = emptyList()
    private var type: Type? = null
    private val adapter: Adapter by lazy {
        Adapter(listener = object : Adapter.OnClickListener {
            override fun onItemClick(item: String, type: Type?) {
                onClick?.invoke(item, type)
                dismiss()
            }
        })
    }

    companion object {
        fun newInstance(
            itemSelected: String,
            list: List<String>,
            type: Type = Type.EXPERIENCE,
        ): BottomSheet {
            val fragment = BottomSheet()
            fragment.itemSelected = itemSelected
            fragment.list = list
            fragment.type = type
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = BottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        binding.rcvItem.adapter = adapter
        adapter.updateData(list, itemSelected ?: "", type)
    }
}