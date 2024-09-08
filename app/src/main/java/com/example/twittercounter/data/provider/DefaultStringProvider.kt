package com.example.twittercounter.data.provider

import android.content.Context
import com.example.twittercounter.R
import com.example.twittercounter.domain.provider.StringProvider
import javax.inject.Inject

class DefaultStringProvider @Inject constructor(private val context: Context) : StringProvider {
    override fun getNoTextToClear() = context.getString(R.string.no_text_to_clear)
    override fun getNoTextToCopy() = context.getString(R.string.no_text_to_copy)
    override fun getTextCopiedToClipboard() = context.getString(R.string.text_copied_to_clipboard)
    override fun getFailedToCopyTextToClipboard() = context.getString(R.string.failed_to_copy_text_to_clipboard)
    override fun getTweetPostedSuccessfully() = context.getString(R.string.tweet_posted_successfully)
}