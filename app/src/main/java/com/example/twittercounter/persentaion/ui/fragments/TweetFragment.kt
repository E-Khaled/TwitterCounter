package com.example.twittercounter.persentaion.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.twittercounter.R
import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.databinding.FragmentTweetBinding
import com.example.twittercounter.persentaion.common.Constants
import com.example.twittercounter.persentaion.common.ProgressBarUtils
import com.example.twittercounter.persentaion.common.toEditable
import com.example.twittercounter.persentaion.common.toast
import com.example.twittercounter.persentaion.viewmodel.TweetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TweetFragment : Fragment() {
    private val tweetViewModel: TweetViewModel by viewModels()
    private val binding by lazy { FragmentTweetBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setup()
        action()
        observers()
        return binding.root
    }

    private fun setup() {
        binding.tvCharRemaining.text = Constants.MAX_TWEET_LENGTH.toString()
        binding.tvTotalCharAllowed.text = "/${Constants.MAX_TWEET_LENGTH.toString()}"
        binding.tvCurrentCharCount.text = "0"
        disablePostBtn()
    }

    private fun action() {
        binding.btnCopyText.setOnClickListener {
            tweetViewModel.copyText(getTweetTextFromUI())
        }
        binding.btnClearText.setOnClickListener {
            tweetViewModel.onClearText(binding.edtTweet.text.toString())
        }
        binding.edtTweet.addTextChangedListener {
            tweetViewModel.onTextChange(it.toString())
        }
        binding.btnPostTweet.setOnClickListener {
            tweetViewModel.postTweet(getTweetTextFromUI())
        }
    }

    private fun observers() {
        lifecycleScope.launch {

            launch {
                tweetViewModel.toastText.collect {
                    toast(it)
                }
            }
            launch {
                tweetViewModel.clearText.collect {
                    binding.edtTweet.text = it.toEditable()
                }
            }
            launch {
                tweetViewModel.characterCount.collect {
                    binding.tvCurrentCharCount.text = it.toString()
                }
            }
            launch {
                tweetViewModel.enableBtn.collect {
                    binding.btnPostTweet.isEnabled = true
                    binding.btnPostTweet.setBackgroundTintList(
                        ContextCompat.getColorStateList(requireContext(), R.color.color_blue)
                    )
                    binding.btnPostTweet.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.white)
                    )
                }
            }
            launch {
                tweetViewModel.disableBtn.collect {
                    disablePostBtn()
                }
            }
            launch {
                tweetViewModel.remainingCharacterCount.collect {
                    binding.tvCharRemaining.text = it.toString()
                }
            }

            launch {
                tweetViewModel.showProgress.collect {
                    if (it) {
                        ProgressBarUtils.showProgressDialog(requireContext())
                    } else {
                        ProgressBarUtils.hideProgressDialog()
                    }
                }
            }

        }
    }

    private fun disablePostBtn() {
        binding.btnPostTweet.isEnabled = false
        binding.btnPostTweet.setBackgroundTintList(
            ContextCompat.getColorStateList(requireContext(), R.color.color_gray)
        )
        binding.btnPostTweet.setTextColor(
            ContextCompat.getColor(requireContext(), R.color.color_text_secondry)
        )
    }

    private fun getTweetTextFromUI(): String {
        return binding.edtTweet.text.toString()
    }
}