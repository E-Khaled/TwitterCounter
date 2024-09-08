package com.example.twittercounter.domain.repository


interface ClipboardHandler {
    fun copyText(label: String, text: String): Boolean
}