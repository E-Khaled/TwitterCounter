import com.example.twittercounter.domain.usecase.tweet.ClearTextUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class ClearTextUseCaseTest {

    private val clearTextUseCase = ClearTextUseCase()

    @Test
    fun `execute should return an empty string`() {
        val result = clearTextUseCase.execute()
        assertEquals("", result)
    }
}
