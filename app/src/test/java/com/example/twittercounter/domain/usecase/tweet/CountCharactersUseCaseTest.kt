package com.example.twittercounter.domain.usecase.tweet

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CountCharactersUseCaseTest {

    private lateinit var countCharactersUseCase: CountCharactersUseCase

    @Before
    fun setup() {
        countCharactersUseCase = CountCharactersUseCase()
    }

    @Test
    fun `test character count within limit`() {
        val input = "Hello, Reviewer from Halan!"

        val result = countCharactersUseCase.execute(input)

        // Assert
        assertEquals(27, result.typed)  // "Hello, Reviewer from Halan!" is 27 characters long
        assertEquals(267, result.remaining) // 280 - 13 = 267
    }

    @Test
    fun `test character count at exact limit`() {
        val input = "a".repeat(280)

        val result = countCharactersUseCase.execute(input)

        assertEquals(280, result.typed)
        assertEquals(0, result.remaining)
    }

    @Test
    fun `test character count exceeding limit`() {
        val input = "a".repeat(300)

        val result = countCharactersUseCase.execute(input)

        assertEquals(300, result.typed)
        assertEquals(-20, result.remaining)
    }
}
