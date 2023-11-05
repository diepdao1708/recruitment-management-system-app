package com.android.recruitment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatDateTime(): String {
    return SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.US).format(this)
}