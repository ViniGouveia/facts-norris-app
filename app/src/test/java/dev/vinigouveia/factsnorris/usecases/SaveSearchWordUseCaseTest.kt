package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCaseImpl
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
 * @author Vinicius Gouveia on 01/07/2021
 */
class SaveSearchWordUseCaseTest {

    private lateinit var useCase: SaveSearchWordUseCase

    private val repository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = SaveSearchWordUseCaseImpl(repository)
    }

    @Test
    fun `should save search word successfully`() = runBlocking {
        val entity = SearchWordEntity(searchWord = "searchWord")

        coEvery { repository.saveSearchWord(any()) } just Runs

        useCase.saveSearchWord("searchWord")

        coVerify(exactly = 1) { repository.saveSearchWord(entity) }

        confirmVerified(repository)
    }
}
