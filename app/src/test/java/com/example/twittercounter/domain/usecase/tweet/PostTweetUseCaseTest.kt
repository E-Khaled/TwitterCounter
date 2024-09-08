import com.example.twittercounter.data.remote.api.ApiResult
import com.example.twittercounter.data.remote.model.ApiError
import com.example.twittercounter.data.remote.model.CreateTweetResp
import com.example.twittercounter.data.remote.model.TweetData
import com.example.twittercounter.domain.model.CreateTweetRequest
import com.example.twittercounter.domain.repository.TweetRepository
import com.example.twittercounter.domain.usecase.tweet.PostTweetUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class PostTweetUseCaseTest {

    private lateinit var tweetRepository: TweetRepository
    private lateinit var postTweetUseCase: PostTweetUseCase

    @Before
    fun setUp() {
        tweetRepository = mock(TweetRepository::class.java)
        postTweetUseCase = PostTweetUseCase(tweetRepository)
    }

    @Test
    fun `execute should emit success result when tweet is posted successfully`() = runBlocking {
        val response = CreateTweetResp(
            data = TweetData(
                text = "Hello World",
                edit_history_tweet_ids = listOf("12345"),
                id = "67890"
            )
        )

        `when`(tweetRepository.postTweet(any())).thenReturn(flow {
            emit(ApiResult.Success(response))
        })

        val flow = postTweetUseCase.execute("Hello World")

        flow.collect { result ->
            assert(result is ApiResult.Success)
            assertEquals(response, (result as ApiResult.Success).data)
        }
        verify(tweetRepository).postTweet(CreateTweetRequest("Hello World"))
    }

    @Test
    fun `execute should emit error result when posting tweet fails`() = runBlocking {
        val apiError = ApiError(500, detail = "Network error")

        `when`(tweetRepository.postTweet(any())).thenReturn(flow {
            emit(ApiResult.Error(apiError))
        })

        val flow = postTweetUseCase.execute("Hello World")

        flow.collect { result ->
            assert(result is ApiResult.Error)
            assertEquals("Network error", (result as ApiResult.Error).error.detail)
        }
        verify(tweetRepository).postTweet(CreateTweetRequest("Hello World"))
    }
}
