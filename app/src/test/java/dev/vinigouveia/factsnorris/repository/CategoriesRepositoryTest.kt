package dev.vinigouveia.factsnorris.repository

import dev.vinigouveia.factsnorris.shared.classes.entities.CategoryEntity
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepositoryImpl
import dev.vinigouveia.factsnorris.shared.room.dao.CategoryDao
import dev.vinigouveia.factsnorris.shared.service.CategoriesService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class CategoriesRepositoryTest {

    private lateinit var repository: CategoriesRepository

    private val service = mockk<CategoriesService>()
    private val categoriesDao = mockk<CategoryDao>()

    @BeforeEach
    fun initialize() {
        repository = CategoriesRepositoryImpl(
            service,
            categoriesDao
        )
    }

    @Test
    fun `should get random suggestions successfully`() = runBlocking {
        val savedCategories = listOf(
            CategoryEntity("category1"),
            CategoryEntity("category2"),
            CategoryEntity("category3"),
            CategoryEntity("category4"),
            CategoryEntity("category5"),
            CategoryEntity("category6"),
            CategoryEntity("category7"),
            CategoryEntity("category8")
        )

        val response = listOf(
            "category1",
            "category2",
            "category3",
            "category4",
            "category5",
            "category6",
            "category7",
            "category8"
        )

        coEvery { categoriesDao.getRandomSuggestions() } returns savedCategories

        assertEquals(response, repository.getRandomSuggestions())

        coVerify(exactly = 1) { categoriesDao.getRandomSuggestions() }

        confirmVerified(service, categoriesDao)
    }

    @Test
    fun `should save categories successfully`() = runBlocking {
        val request = listOf(
            CategoryEntity("category1"),
            CategoryEntity("category2"),
            CategoryEntity("category3"),
            CategoryEntity("category4"),
            CategoryEntity("category5"),
            CategoryEntity("category6"),
            CategoryEntity("category7"),
            CategoryEntity("category8")
        )

        val categories = listOf(
            "category1",
            "category2",
            "category3",
            "category4",
            "category5",
            "category6",
            "category7",
            "category8"
        )

        coEvery { categoriesDao.insertAllCategories(any()) } just Runs

        repository.saveCategories(categories)

        coVerify(exactly = 1) { categoriesDao.insertAllCategories(request) }

        confirmVerified(service, categoriesDao)
    }

    @Test
    fun `should fetch categories successfully`() = runBlocking {
        val response = listOf(
            "category1",
            "category2",
            "category3",
            "category4",
            "category5",
            "category6",
            "category7",
            "category8"
        )

        coEvery { service.fetchCategories() } returns response

        assertEquals(response, repository.fetchCategories())

        coVerify(exactly = 1) { service.fetchCategories() }

        confirmVerified(service, categoriesDao)
    }
}
