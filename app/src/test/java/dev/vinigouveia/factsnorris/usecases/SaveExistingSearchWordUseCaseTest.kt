package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.usecases.DeleteSearchWordByWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveExistingSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveExistingSearchWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class SaveExistingSearchWordUseCaseTest {

    private lateinit var useCase: SaveExistingSearchWordUseCase

    private val deleteSearchWordByWordUseCase = mockk<DeleteSearchWordByWordUseCase>()
    private val saveSearchWordUseCase = mockk<SaveSearchWordUseCase>()

    @BeforeEach
    fun initialize() {
        useCase = SaveExistingSearchWordUseCaseImpl(
            deleteSearchWordByWordUseCase,
            saveSearchWordUseCase
        )
    }

    @Test
    fun `should delete last saved search word and save latest search`() = runBlocking {
        coEvery { deleteSearchWordByWordUseCase.deleteSearchWordByWord(any()) } just Runs
        coEvery { saveSearchWordUseCase.saveSearchWord(any()) } just Runs

        useCase.saveExistingSearchWord("searchWord")

        coVerify(exactly = 1) {
            deleteSearchWordByWordUseCase.deleteSearchWordByWord("searchWord")
            saveSearchWordUseCase.saveSearchWord("searchWord")
        }
    }
}
