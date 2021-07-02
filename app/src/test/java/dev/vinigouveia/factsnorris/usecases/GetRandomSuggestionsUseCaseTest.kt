package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class GetRandomSuggestionsUseCaseTest {

    private lateinit var useCase: GetRandomSuggestionsUseCase

    private val repository = mockk<CategoriesRepository>()

    @BeforeEach
    fun initialize() {
        useCase = GetRandomSuggestionsUseCaseImpl(repository)
    }

    @Test
    fun `should get random suggestions successfully`() = runBlocking {
        val response = listOf(
            "suggestion1",
            "suggestion2",
            "suggestion3"
        )

        coEvery { repository.getRandomSuggestions() } returns response

        assertEquals(response, useCase.getRandomSuggestions())

        coVerify(exactly = 1) { repository.getRandomSuggestions() }

        confirmVerified(repository)
    }
}
