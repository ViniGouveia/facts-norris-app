package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyCategoriesListReturnedException
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.usecases.FetchCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchCategoriesUseCaseImpl
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
class FetchCategoriesUseCaseTest {

    private lateinit var useCase: FetchCategoriesUseCase

    private val repository = mockk<CategoriesRepository>()

    @BeforeEach
    fun initialize() {
        useCase = FetchCategoriesUseCaseImpl(repository)
    }

    @Test
    fun `should fetch categories successfully`() = runBlocking {
        val response = listOf(
            "category1",
            "category2"
        )

        coEvery { repository.fetchCategories() } returns response

        assertEquals(response, useCase.fetchCategories())

        coVerify(exactly = 1) {
            repository.fetchCategories()
        }

        confirmVerified(repository)
    }

    @Test
    fun `should throw EmptyCategoriesListReturnedException when return an empty categories list`() =
        runBlocking {
            coEvery { repository.fetchCategories() } throws EmptyCategoriesListReturnedException

            assertThrows<EmptyCategoriesListReturnedException> { useCase.fetchCategories() }

            coVerify(exactly = 1) { repository.fetchCategories() }

            confirmVerified(repository)
        }
}
