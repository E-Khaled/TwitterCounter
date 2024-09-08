package com.example.twittercounter.persentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.domain.provider.StringProvider
import com.example.twittercounter.domain.usecase.tweet.ClearTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CopyTextUseCase
import com.example.twittercounter.domain.usecase.tweet.CountCharactersUseCase
import com.example.twittercounter.domain.usecase.tweet.PostTweetUseCase
import com.example.twittercounter.persentaion.common.Constants.MAX_TWEET_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetViewModel @Inject constructor(
    private val countCharactersUseCase: CountCharactersUseCase,
    private val postTweetUseCase: PostTweetUseCase,
    private val clearTextUseCase: ClearTextUseCase,
    private val copyTextUseCase: CopyTextUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _characterCount = MutableSharedFlow<Int>()
    val characterCount = _characterCount.asSharedFlow()

    private val _remainingCharacterCount = MutableSharedFlow<Int>()
    val remainingCharacterCount = _remainingCharacterCount.asSharedFlow()

    private val _isPostEnabled = MutableSharedFlow<Boolean>()
    val isPostEnabled = _isPostEnabled.asSharedFlow()

    private val _enableBtn = MutableSharedFlow<Boolean>()
    val enableBtn = _enableBtn.asSharedFlow()

    private val _disableBtn = MutableSharedFlow<Boolean>()
    val disableBtn = _disableBtn.asSharedFlow()

    private val _toastText = MutableSharedFlow<String>()
    val toastText = _toastText.asSharedFlow()

    private val _clearText = MutableSharedFlow<String>()
    val clearText = _clearText.asSharedFlow()

    private val _showProgress = MutableSharedFlow<Boolean>()
    val showProgress = _showProgress.asSharedFlow()

    fun postTweet(tweet: String) {
        viewModelScope.launch {
            postTweetUseCase.execute(tweet).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        _showProgress.emit(false)
                        _toastText.emit(result.error.detail)
                    }

                    is ApiResult.Loading -> {
                        _showProgress.emit(true)
                    }

                    is ApiResult.Success -> {
                        _showProgress.emit(false)
                        _toastText.emit(stringProvider.getTweetPostedSuccessfully())
                    }
                }
            }
        }
    }

    //TODO remove hardCoded Strings
    fun onClearText(text: String) {
        viewModelScope.launch {
            if (text.isEmpty() || text.isBlank())
                _toastText.emit(stringProvider.getNoTextToClear())
            else
                _clearText.emit(clearTextUseCase.execute())
        }
    }

    fun copyText(text: String) {
        viewModelScope.launch {
            if (text.isEmpty() || text.isBlank()) {
                _toastText.emit(stringProvider.getNoTextToCopy())
                return@launch
            } else {
                val copyTextResult = copyTextUseCase.execute(text)
                if (!copyTextResult) {
                    _toastText.emit(stringProvider.getFailedToCopyTextToClipboard())
                }
            }

        }
    }

    fun isPostEnabled(characterCount: Int) {
        viewModelScope.launch {
            if (characterCount > 0 && characterCount <= MAX_TWEET_LENGTH) {
                _enableBtn.emit(true)
            } else
                _disableBtn.emit(true)
        }
    }

    fun onTextChange(text: String) {
        viewModelScope.launch {
            val characterCount = countCharactersUseCase.getTextLength(text)

            //TODO maybe seperate this in a function calcAndEmitRemainingCharacterCount()
            val remainingCharacterCount = MAX_TWEET_LENGTH - characterCount
            if (remainingCharacterCount >= 0) {
                _remainingCharacterCount.emit(remainingCharacterCount)
            } else {
                _remainingCharacterCount.emit(0)
            }

            _characterCount.emit(characterCount)
            isPostEnabled(characterCount)
        }
    }
}