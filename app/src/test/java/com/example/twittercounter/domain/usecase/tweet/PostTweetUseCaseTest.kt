package com.example.twittercounter.domain.usecase.tweet

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PostTweetUseCaseTest {

    private lateinit var repository: TwitterRepository
    private lateinit var postTweetUseCase: PostTweetUseCase

    @Before
    fun setup() {
        repository = mock(TwitterRepository::class.java)
        postTweetUseCase = PostTweetUseCase(repository)
    }

    @Test
    fun `test successful tweet post`(): Unit = runBlocking {
        val tweetText = "This is a test tweet."
        val successMessage = "Tweet posted successfully"
        `when`(repository.postTweet(tweetText)).thenReturn(Result.success(successMessage))

        val result = postTweetUseCase.execute(tweetText)

        assertTrue(result.isSuccess)
        assertEquals(successMessage, result.getOrNull())
        verify(repository).postTweet(tweetText)  // Ensure repository method is called
    }

    @Test
    fun `test tweet exceeding character limit`(): Unit = runBlocking {
        val tweetText = "a".repeat(300)

        val result = postTweetUseCase.execute(tweetText)

        assertTrue(result.isFailure)
        assertEquals("Tweet exceeds 280 characters", result.exceptionOrNull()?.message)
        verify(repository, never()).postTweet(anyString())  // Ensure repository method is NOT called
    }

    @Test
    fun `test repository failure when posting tweet`(): Unit = runBlocking {
        val tweetText = "This is a test tweet."
        val exception = Exception("Network error")
        `when`(repository.postTweet(tweetText)).thenReturn(Result.failure(exception))

        val result = postTweetUseCase.execute(tweetText)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
        verify(repository).postTweet(tweetText)
    }
}
