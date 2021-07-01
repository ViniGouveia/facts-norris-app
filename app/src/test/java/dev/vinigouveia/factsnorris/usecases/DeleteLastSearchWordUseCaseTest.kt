package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.DeleteLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.DeleteLastSearchWordUseCaseImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
class DeleteLastSearchWordUseCaseTest {

    private lateinit var useCase: DeleteLastSearchWordUseCase

    private val repository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = DeleteLastSearchWordUseCaseImpl(repository)
    }

    @Test
    fun `should delete last search word successfully`() = runBlocking {
        coEvery { repository.deleteLastSearchWord() } just Runs

        useCase.deleteLastSearchWord()

        coVerify(exactly = 1) {
            repository.deleteLastSearchWord()
        }

        confirmVerified(repository)
    }
}
