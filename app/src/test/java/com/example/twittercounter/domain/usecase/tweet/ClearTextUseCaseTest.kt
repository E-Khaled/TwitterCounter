package com.example.twittercounter.domain.usecase.tweet

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ClearTextUseCaseTest{

    private lateinit var clearTextUseCase: ClearTextUseCase

    @Before
    fun setup() {
        clearTextUseCase = ClearTextUseCase()
    }

    @Test
    fun `test clear text`() {
        val result = clearTextUseCase.execute()

        assertEquals("", result)
    }
}