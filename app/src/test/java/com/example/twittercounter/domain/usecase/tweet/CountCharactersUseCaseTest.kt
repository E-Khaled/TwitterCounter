import com.example.twittercounter.domain.usecase.tweet.CountCharactersUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CountCharactersUseCaseTest {

    private lateinit var countCharactersUseCase: CountCharactersUseCase

    @Before
    fun setUp() {
        countCharactersUseCase = CountCharactersUseCase()
    }

    @Test
    fun `getTextLength should return correct length for normal text`() {
        val result = countCharactersUseCase.getTextLength("Hello, Reviewer from Halan!")
        assertEquals(27, result)  // "Hello, Reviewer from Halan!" is 27 characters long
    }

    @Test
    fun `getTextLength should count emojis as 2 characters`() {
        val result = countCharactersUseCase.getTextLength("Hello 😊")
        assertEquals(8, result)  // "Hello 😊" counts the emoji as 2 characters
    }

    @Test
    fun `getTextLength should count URLs as 23 characters`() {
        val result = countCharactersUseCase.getTextLength("Check this out: https://example.com")
        assertEquals(39, result)  // "Check this out: " (17) + URL (23) - (3)
    }

    @Test
    fun `getTextLength should count special languages as 2 characters each`() {
        val result = countCharactersUseCase.getTextLength("测试")
        assertEquals(4, result)  // "测试" is 2 Chinese characters, each counted as 2
    }
}
