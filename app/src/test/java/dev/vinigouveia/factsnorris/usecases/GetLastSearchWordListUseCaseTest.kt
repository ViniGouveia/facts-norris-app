package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCaseImpl
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
class GetLastSearchWordListUseCaseTest {

    private lateinit var useCase: GetLastSearchWordListUseCase

    private val repository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = GetLastSearchWordListUseCaseImpl(repository)
    }

    @Test
    fun `should get last search words successfully`() = runBlocking {
        val response = listOf(
            "test",
            "search"
        )

        coEvery { repository.getLastSearchWordList() } returns response

        assertEquals(response, useCase.getLastSearchWordList())

        coVerify(exactly = 1) { repository.getLastSearchWordList() }

        confirmVerified(repository)
    }
}
