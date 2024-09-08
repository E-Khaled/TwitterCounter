import com.example.twittercounter.domain.repository.ClipboardHandler
import com.example.twittercounter.domain.usecase.tweet.CopyTextUseCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CopyTextUseCaseTest {

    private lateinit var clipboardHandler: ClipboardHandler
    private lateinit var copyTextUseCase: CopyTextUseCase

    @Before
    fun setUp() {
        clipboardHandler = mock(ClipboardHandler::class.java)
        copyTextUseCase = CopyTextUseCase(clipboardHandler)
    }

    @Test
    fun `execute should return true when text is copied successfully`() {
        `when`(clipboardHandler.copyText(anyString(), anyString())).thenReturn(true)

        val result = copyTextUseCase.execute("Some text")

        assertTrue(result)
        verify(clipboardHandler).copyText("tweetText", "Some text")
    }

    @Test
    fun `execute should return false when text is not copied successfully`() {
        `when`(clipboardHandler.copyText(anyString(), anyString())).thenReturn(false)

        val result = copyTextUseCase.execute("Some text")

        assertFalse(result)
        verify(clipboardHandler).copyText("tweetText", "Some text")
    }
}
