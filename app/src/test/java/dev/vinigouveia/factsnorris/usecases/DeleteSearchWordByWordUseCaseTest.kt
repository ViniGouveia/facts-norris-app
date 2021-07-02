package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.DeleteSearchWordByWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.DeleteSearchWordByWordUseCaseImpl
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
class DeleteSearchWordByWordUseCaseTest {

    private lateinit var useCase: DeleteSearchWordByWordUseCase

    private val repository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = DeleteSearchWordByWordUseCaseImpl(repository)
    }

    @Test
    fun `should delete expecified search word successfully`() = runBlocking {
        val searchWord = "test"

        coEvery { repository.deleteSearchWordByWord(any()) } just Runs

        useCase.deleteSearchWordByWord(searchWord)

        coVerify(exactly = 1) { repository.deleteSearchWordByWord(searchWord) }

        confirmVerified(repository)
    }
}
