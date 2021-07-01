package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.usecases.IsLastSearchesListFullUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsLastSearchesListFullUseCaseImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class IsLastSearchesListFullUseCaseTest {

    private lateinit var useCase: IsLastSearchesListFullUseCase

    @BeforeEach
    fun initialize() {
        useCase = IsLastSearchesListFullUseCaseImpl()
    }

    @Test
    fun `should return true if last searches list is full`() = runBlocking {
        val lastSearches = listOf(
            "lastSearch1",
            "lastSearch2",
            "lastSearch3",
            "lastSearch4",
            "lastSearch5",
            "lastSearch6",
            "lastSearch7",
            "lastSearch8"
        )
        assertEquals(true, useCase.isLastSearchesListFull(lastSearches.size))
    }

    @Test
    fun `should return false if last searches list is not full`() = runBlocking {
        val lastSearches = listOf(
            "lastSearch1",
            "lastSearch2",
            "lastSearch3",
            "lastSearch4",
            "lastSearch5",
            "lastSearch6"
        )
        assertEquals(false, useCase.isLastSearchesListFull(lastSearches.size))
    }
}
