package com.android.recruitment.utils

import android.util.Base64

fun String.base64Decoded(): String? {
    return try {
        val bytes = Base64.decode(this, Base64.URL_SAFE)
        String(bytes, Charsets.UTF_8)
    } catch (e: Exception) {
        null
    }
}