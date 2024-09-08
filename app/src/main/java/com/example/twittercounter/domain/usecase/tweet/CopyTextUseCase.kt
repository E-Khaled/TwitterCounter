package com.example.twittercounter.domain.usecase.tweet

import com.example.twittercounter.domain.repository.ClipboardHandler
import javax.inject.Inject

class CopyTextUseCase @Inject constructor(private val clipboardHandler: ClipboardHandler) {
    fun execute(text: String): Boolean {
        return clipboardHandler.copyText("tweetText", text)
    }
}