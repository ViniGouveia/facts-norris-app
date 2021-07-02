package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.usecases.AreCategoriesSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.AreCategoriesSavedUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
class AreCategoriesSavedUseCaseTest {

    private lateinit var useCase: AreCategoriesSavedUseCase

    private val getRandomSuggestionsUseCase = mockk<GetRandomSuggestionsUseCase>()

    @BeforeEach
    fun initialize() {
        useCase = AreCategoriesSavedUseCaseImpl(getRandomSuggestionsUseCase)
    }

    @Test
    fun `return true if have saved categories`() = runBlocking {
        val response = listOf(
            "category1",
            "category2"
        )

        coEvery { getRandomSuggestionsUseCase.getRandomSuggestions() } returns response

        assertEquals(true, useCase.areSavedCategories())

        coVerify(exactly = 1) {
            getRandomSuggestionsUseCase.getRandomSuggestions()
        }

        confirmVerified(getRandomSuggestionsUseCase)
    }

    @Test
    fun `return false if do not have saved categories`() = runBlocking {
        val response = listOf<String>()

        coEvery { getRandomSuggestionsUseCase.getRandomSuggestions() } returns response

        assertEquals(false, useCase.areSavedCategories())

        coVerify(exactly = 1) {
            getRandomSuggestionsUseCase.getRandomSuggestions()
        }

        confirmVerified(getRandomSuggestionsUseCase)
    }
}
