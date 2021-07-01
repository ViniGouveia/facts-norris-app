package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsSearchWordSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsSearchWordSavedUseCaseImpl
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
class IsSearchWordSavedUseCaseTest {

    private lateinit var useCase: IsSearchWordSavedUseCase

    private val getLastSearchWordListUseCase = mockk<GetLastSearchWordListUseCase>()

    @BeforeEach
    fun initialize() {
        useCase = IsSearchWordSavedUseCaseImpl(getLastSearchWordListUseCase)
    }

    @Test
    fun `should return true if last searches list contains expecified search word`() = runBlocking {
        val response = listOf(
            "searchWord1",
            "searchWord2"
        )

        coEvery { getLastSearchWordListUseCase.getLastSearchWordList() } returns response

        assertEquals(true, useCase.isSearchWordSaved("searchWord1"))

        coVerify(exactly = 1) { getLastSearchWordListUseCase.getLastSearchWordList() }

        confirmVerified(getLastSearchWordListUseCase)
    }

    @Test
    fun `should return false if search word are not in last searches list`() = runBlocking {
        val response = listOf(
            "searchWord1",
            "searchWord2"
        )

        coEvery { getLastSearchWordListUseCase.getLastSearchWordList() } returns response

        assertEquals(false, useCase.isSearchWordSaved("searchWord4"))

        coVerify(exactly = 1) { getLastSearchWordListUseCase.getLastSearchWordList() }

        confirmVerified(getLastSearchWordListUseCase)
    }
}
