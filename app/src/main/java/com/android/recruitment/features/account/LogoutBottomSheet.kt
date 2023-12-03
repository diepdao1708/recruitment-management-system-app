package com.android.recruitment.features.account

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.recruitment.R
import com.android.recruitment.databinding.BottomSheetLogoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogoutBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLogoutBinding
    var onClick: (() -> Unit)? = null


    companion object {
        fun newInstance(): LogoutBottomSheet {
            return LogoutBottomSheet()
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
        binding = BottomSheetLogoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        binding.root.setOnClickListener {
            onClick?.invoke()
        }
    }
}