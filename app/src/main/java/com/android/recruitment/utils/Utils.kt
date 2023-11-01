package com.android.recruitment.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object DocumentUtils {
    fun getFile(context: Context?, documentUri: Uri): File {
        val inputStream = context?.contentResolver?.openInputStream(documentUri)
        var file: File
        inputStream.use { input ->
            file =
                File(context?.cacheDir, System.currentTimeMillis().toString() + ".pdf")
            FileOutputStream(file).use { output ->
                val buffer =
                    ByteArray(4 * 1024) // or other buffer size
                var read: Int = -1
                while (input?.read(buffer).also {
                        if (it != null) {
                            read = it
                        }
                    } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return file
    }
}