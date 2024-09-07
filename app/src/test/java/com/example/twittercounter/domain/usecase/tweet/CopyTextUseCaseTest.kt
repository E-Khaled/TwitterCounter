package com.example.twittercounter.domain.usecase.tweet

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CopyTextUseCaseTest {

    private lateinit var copyTextUseCase: CopyTextUseCase

    @Before
    fun setup() {
        copyTextUseCase = CopyTextUseCase()
    }

    @Test
    fun `test copy text`() {
        val inputText = "This text should be copied."

        val result = copyTextUseCase.execute(inputText)

        assertEquals(inputText, result)
    }
}

