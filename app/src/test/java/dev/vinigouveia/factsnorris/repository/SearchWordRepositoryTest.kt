package dev.vinigouveia.factsnorris.repository

import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepositoryImpl
import dev.vinigouveia.factsnorris.shared.room.dao.SearchWordDao
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
class SearchWordRepositoryTest {

    private lateinit var repository: SearchWordRepository

    private val searchWordDao = mockk<SearchWordDao>()

    @BeforeEach
    fun initialize() {
        repository = SearchWordRepositoryImpl(searchWordDao)
    }

    @Test
    fun `should save search word successfully`() = runBlocking {
        val entity = SearchWordEntity(id = 0, searchWord = "searchWord")

        coEvery { searchWordDao.insertSearchWord(any()) } just Runs

        repository.saveSearchWord(entity)

        coVerify(exactly = 1) { searchWordDao.insertSearchWord(entity) }

        confirmVerified(searchWordDao)
    }

    @Test
    fun `should get latest search word successfully`() = runBlocking {
        val entity = SearchWordEntity(id = 0, searchWord = "searchWord")

        coEvery { searchWordDao.getLatestSearchWord() } returns entity

        assertEquals("searchWord", repository.getLatestSearchWord())

        coVerify(exactly = 1) { searchWordDao.getLatestSearchWord() }

        confirmVerified(searchWordDao)
    }

    @Test
    fun `should get last searches list successfully`() = runBlocking {
        val entities = listOf(
            SearchWordEntity(id = 0, searchWord = "searchWord"),
            SearchWordEntity(id = 1, searchWord = "searchWord1"),
            SearchWordEntity(id = 2, searchWord = "searchWord2"),
            SearchWordEntity(id = 3, searchWord = "searchWord3")
        )

        val lastSearches = listOf(
            "searchWord",
            "searchWord1",
            "searchWord2",
            "searchWord3"
        )

        coEvery { searchWordDao.getLastSearchWordList() } returns entities

        assertEquals(lastSearches, repository.getLastSearchWordList())

        coVerify(exactly = 1) { searchWordDao.getLastSearchWordList() }

        confirmVerified(searchWordDao)
    }

    @Test
    fun `should delete last search word successfully`() = runBlocking {
        coEvery { searchWordDao.deleteLastSearchWord() } just Runs

        repository.deleteLastSearchWord()

        coVerify(exactly = 1) { searchWordDao.deleteLastSearchWord() }

        confirmVerified(searchWordDao)
    }

    @Test
    fun `should delete expecified search word successfully`() = runBlocking {
        coEvery { searchWordDao.deleteSearchWordByWord(any()) } just Runs

        repository.deleteSearchWordByWord("searchWord")

        coVerify(exactly = 1) { searchWordDao.deleteSearchWordByWord("searchWord") }

        confirmVerified(searchWordDao)
    }
}
