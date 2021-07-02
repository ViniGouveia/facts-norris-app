package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.SearchTermNotFoundException
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.GetLatestSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLatestSearchWordUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
class GetLatestSearchWordUseCaseTest {

    private lateinit var useCase: GetLatestSearchWordUseCase

    private val repository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = GetLatestSearchWordUseCaseImpl(repository)
    }

    @Test
    fun `should get latest search word successfully`() = runBlocking {
        val response = "lastSearch"

        coEvery { repository.getLatestSearchWord() } returns response

        assertEquals(response, useCase.getLatestSearchWord())

        coVerify(exactly = 1) {
            repository.getLatestSearchWord()
        }

        confirmVerified(repository)
    }

    @Test
    fun `should throw SearchTermNotFoundException when not found latest search word`() =
        runBlocking {
            coEvery { repository.getLatestSearchWord() } throws SearchTermNotFoundException

            assertThrows<SearchTermNotFoundException> { useCase.getLatestSearchWord() }

            coVerify(exactly = 1) { repository.getLatestSearchWord() }

            confirmVerified(repository)
        }
}
