package com.example.twittercounter.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.twittercounter.domain.repository.ClipboardHandler

class ClipboardHandlerImpl(private val context: Context) : ClipboardHandler {

    override fun copyText(label: String, text: String): Boolean {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        return if (clipboardManager != null) {
            val clip = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(clip)
            true
        } else {
            false
        }
    }
}