package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyFactsListReturnedException
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
class FetchFactsUseCaseTest {

    private lateinit var useCase: FetchFactsUseCase

    private val repository = mockk<FactsRepository>()

    @BeforeEach
    fun initialize() {
        useCase = FetchFactsUseCaseImpl(repository)
    }

    @Test
    fun `should fetch facts successfully`() = runBlocking {
        val response = listOf(
            Fact("id", null, "url", "description"),
            Fact("id", null, "url", "description")
        )

        coEvery { repository.fetchFacts(any()) } returns response

        Assertions.assertEquals(response, useCase.fetchFacts(""))

        coVerify(exactly = 1) {
            repository.fetchFacts("")
        }

        confirmVerified(repository)
    }

    @Test
    fun `should throw EmptyFactsListReturnedException when return an empty facts list`() =
        runBlocking {
            coEvery { repository.fetchFacts(any()) } throws EmptyFactsListReturnedException

            assertThrows<EmptyFactsListReturnedException> { useCase.fetchFacts("") }

            coVerify(exactly = 1) { repository.fetchFacts("") }

            confirmVerified(repository)
        }
}
