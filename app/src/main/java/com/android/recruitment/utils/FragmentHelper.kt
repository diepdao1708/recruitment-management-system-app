package com.android.recruitment.utils

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboardOnClickOutside(view: View) {
    if (view !is EditText) {
        view.setOnTouchListener { v, _ ->
            v.performClick()
            activity?.hideKeyboard()
            false
        }
    }

    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            hideKeyboardOnClickOutside(child)
        }
    }
}