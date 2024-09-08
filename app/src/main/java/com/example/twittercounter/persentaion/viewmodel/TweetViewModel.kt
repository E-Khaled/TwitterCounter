package com.example.twittercounter.persentaion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twittercounter.data.remote.api.ApiResult
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
    private val copyTextUseCase: CopyTextUseCase
) : ViewModel() {

    private val _characterCount = MutableSharedFlow<Int>()
    val characterCount = _characterCount.asSharedFlow()

    private val _remainingCharacterCount = MutableSharedFlow<Int>()
    val remainingCharacterCount = _remainingCharacterCount.asSharedFlow()

    private val _isValidCharacterCount = MutableSharedFlow<Boolean>()
    val isValidCharacterCount = _isValidCharacterCount.asSharedFlow()

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
                        _toastText.emit("Congrats")
                    }
                }
            }
        }
    }
//TODO exceeded max bool flow

    fun onClearText() {
        viewModelScope.launch {
            _clearText.emit(clearTextUseCase.execute())
        }
    }

    fun copyText(text: String) {
        viewModelScope.launch {
            val copyTextResult = copyTextUseCase.execute(text)
            if (copyTextResult) {
                _toastText.emit("Text copied to clipboard")
            } else {
                _toastText.emit("Failed to copy text to clipboard")
            }

        }
    }

    fun onTextChange(text: String) {
        viewModelScope.launch {
            val characterCount = countCharactersUseCase.getTextLength(text)
            //TODO maybe seperate this in a function calcAndEmitRemainingCharacterCount()
            val remainingCharacterCount = MAX_TWEET_LENGTH - characterCount
            if (remainingCharacterCount >= 0) {
                _remainingCharacterCount.emit(remainingCharacterCount)
            }
            else {
                _remainingCharacterCount.emit(0)
            }

            _characterCount.emit(characterCount)
        }
    }
}