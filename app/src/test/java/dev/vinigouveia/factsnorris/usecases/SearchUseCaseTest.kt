package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.shared.classes.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCaseImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchUseCaseTest {

    private lateinit var useCase: SearchUseCase

    private val searchWordRepository = mockk<SearchWordRepository>()
    private val categoriesRepository = mockk<CategoriesRepository>()

    @BeforeEach
    fun initialize() {
        useCase = SearchUseCaseImpl(searchWordRepository, categoriesRepository)
    }

    @Test
    fun `return true if have saved categories`() = runBlocking {
        val response = listOf(
            "category1",
            "category2"
        )

        coEvery { categoriesRepository.getRandomSuggestions() } returns response

        Assertions.assertEquals(true, useCase.areSavedCategories())

        coVerify(exactly = 1) {
            categoriesRepository.getRandomSuggestions()
        }

        confirmVerified(categoriesRepository)
    }

    @Test
    fun `return false if do not have saved categories`() = runBlocking {
        val response = listOf<String>()

        coEvery { categoriesRepository.getRandomSuggestions() } returns response

        Assertions.assertEquals(false, useCase.areSavedCategories())

        coVerify(exactly = 1) {
            categoriesRepository.getRandomSuggestions()
        }

        confirmVerified(categoriesRepository)
    }

    @Test
    fun `should delete last search word successfully`() = runBlocking {
        coEvery { searchWordRepository.deleteLastSearchWord() } just Runs

        useCase.deleteLastSearchWord()

        coVerify(exactly = 1) {
            searchWordRepository.deleteLastSearchWord()
        }

        confirmVerified(searchWordRepository)
    }

    @Test
    fun `should save existing search word successfully`() = runBlocking {
        val searchWord = "test"
        val searchWordEntity = SearchWordEntity(searchWord = "test")

        coEvery { searchWordRepository.deleteSearchWordByWord(any()) } just Runs
        coEvery { searchWordRepository.saveSearchWord(any()) } just Runs

        useCase.saveExistingSearchWord(searchWord)

        coVerify(exactly = 1) {
            searchWordRepository.deleteSearchWordByWord(searchWord)
            searchWordRepository.saveSearchWord(searchWordEntity)
        }

        confirmVerified(searchWordRepository)
    }

    @Test
    fun `should fetch categories successfully`() = runBlocking {
        val response = listOf(
            "category1",
            "category2"
        )

        coEvery { categoriesRepository.fetchCategories() } returns response

        Assertions.assertEquals(response, useCase.fetchCategories())

        coVerify(exactly = 1) {
            categoriesRepository.fetchCategories()
        }

        confirmVerified(categoriesRepository)
    }

    @Test
    fun `should get last search words successfully`() = runBlocking {
        val response = listOf(
            "test",
            "search"
        )

        coEvery { searchWordRepository.getLastSearchWordList() } returns response

        Assertions.assertEquals(response, useCase.getLastSearchWordList())

        coVerify(exactly = 1) { searchWordRepository.getLastSearchWordList() }

        confirmVerified(searchWordRepository)
    }

    @Test
    fun `should get random suggestions successfully`() = runBlocking {
        val response = listOf(
            "suggestion1",
            "suggestion2",
            "suggestion3"
        )

        coEvery { categoriesRepository.getRandomSuggestions() } returns response

        Assertions.assertEquals(response, useCase.getRandomSuggestions())

        coVerify(exactly = 1) { categoriesRepository.getRandomSuggestions() }

        confirmVerified(categoriesRepository)
    }

    @Test
    fun `should return true if last searches list is full`() = runBlocking {
        val lastSearches = listOf(
            "lastSearch1",
            "lastSearch2",
            "lastSearch3",
            "lastSearch4",
            "lastSearch5",
            "lastSearch6",
            "lastSearch7",
            "lastSearch8"
        )
        Assertions.assertEquals(true, useCase.isLastSearchesListFull(lastSearches.size))
    }

    @Test
    fun `should return false if last searches list is not full`() = runBlocking {
        val lastSearches = listOf(
            "lastSearch1",
            "lastSearch2",
            "lastSearch3",
            "lastSearch4",
            "lastSearch5",
            "lastSearch6"
        )
        Assertions.assertEquals(false, useCase.isLastSearchesListFull(lastSearches.size))
    }

    @Test
    fun `should return true if last searches list contains expecified search word`() = runBlocking {
        val response = listOf(
            "searchWord1",
            "searchWord2"
        )

        coEvery { searchWordRepository.getLastSearchWordList() } returns response

        Assertions.assertEquals(true, useCase.isSearchWordSaved("searchWord1"))

        coVerify(exactly = 1) { searchWordRepository.getLastSearchWordList() }

        confirmVerified(searchWordRepository)
    }

    @Test
    fun `should return false if search word are not in last searches list`() = runBlocking {
        val response = listOf(
            "searchWord1",
            "searchWord2"
        )

        coEvery { searchWordRepository.getLastSearchWordList() } returns response

        Assertions.assertEquals(false, useCase.isSearchWordSaved("searchWord4"))

        coVerify(exactly = 1) { searchWordRepository.getLastSearchWordList() }

        confirmVerified(searchWordRepository)
    }

    @Test
    fun `should save categories successfully`() = runBlocking {
        val request = listOf(
            "category1",
            "category2"
        )

        coEvery { categoriesRepository.saveCategories(any()) } just Runs

        useCase.saveCategories(request)

        coVerify(exactly = 1) { categoriesRepository.saveCategories(request) }

        confirmVerified(categoriesRepository)
    }

    @Test
    fun `should save search word successfully`() = runBlocking {
        val entity = SearchWordEntity(searchWord = "searchWord")

        coEvery { searchWordRepository.saveSearchWord(any()) } just Runs

        useCase.saveSearchWord("searchWord")

        coVerify(exactly = 1) { searchWordRepository.saveSearchWord(entity) }

        confirmVerified(searchWordRepository)
    }
}