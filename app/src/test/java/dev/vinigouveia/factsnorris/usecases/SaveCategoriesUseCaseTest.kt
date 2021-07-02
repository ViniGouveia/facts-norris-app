package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.usecases.SaveCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveCategoriesUseCaseImpl
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
class SaveCategoriesUseCaseTest {

    private lateinit var useCase: SaveCategoriesUseCase

    private val repository = mockk<CategoriesRepository>()

    @BeforeEach
    fun initialize() {
        useCase = SaveCategoriesUseCaseImpl(repository)
    }

    @Test
    fun `should save categories successfully`() = runBlocking {
        val request = listOf(
            "category1",
            "category2"
        )

        coEvery { repository.saveCategories(any()) } just Runs

        useCase.saveCategories(request)

        coVerify(exactly = 1) { repository.saveCategories(request) }

        confirmVerified(repository)
    }
}
