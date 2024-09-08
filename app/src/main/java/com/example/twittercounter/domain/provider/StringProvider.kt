package com.example.twittercounter.domain.provider

interface StringProvider {
    fun getNoTextToClear(): String
    fun getNoTextToCopy(): String
    fun getTextCopiedToClipboard(): String
    fun getFailedToCopyTextToClipboard(): String
    fun getTweetPostedSuccessfully(): String
}