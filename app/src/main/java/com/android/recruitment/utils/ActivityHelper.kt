package com.android.recruitment.utils

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setFullScreen() {
    supportActionBar?.hide()

    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun Activity.hideKeyboard() {
    currentFocus?.let {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

// https://stackoverflow.com/a/36259261
fun Activity.setKeyboardVisibilityListener(onVisibilityChanged: (Boolean) -> Unit) {
    val parentView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    parentView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        private var alreadyOpen = false
        private val defaultKeyboardHeightDP = 100
        private val estimatedKeyboardDP = defaultKeyboardHeightDP + 48
        private val rect: Rect = Rect()
        override fun onGlobalLayout() {
            val estimatedKeyboardHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                estimatedKeyboardDP.toFloat(),
                parentView.resources.displayMetrics
            ).toInt()
            parentView.getWindowVisibleDisplayFrame(rect)
            val heightDiff: Int = parentView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight
            if (isShown == alreadyOpen) {
                // "Keyboard state: Ignoring global layout change..."
                return
            }
            alreadyOpen = isShown
            onVisibilityChanged(isShown)
        }
    })
}