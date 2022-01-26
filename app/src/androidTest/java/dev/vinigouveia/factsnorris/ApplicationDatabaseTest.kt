package dev.vinigouveia.factsnorris

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.vinigouveia.factsnorris.shared.classes.entities.CategoryEntity
import dev.vinigouveia.factsnorris.shared.classes.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.room.dao.CategoryDao
import dev.vinigouveia.factsnorris.shared.room.dao.SearchWordDao
import dev.vinigouveia.factsnorris.shared.room.database.ApplicationDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
@RunWith(AndroidJUnit4::class)
internal class ApplicationDatabaseTest {

    private lateinit var database: ApplicationDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var searchWordDao: SearchWordDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build()
        categoryDao = database.categoryDao()
        searchWordDao = database.searchWordDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun shouldInsertAllCategoriesInCategoriesTableAndGetRandomSuggestions() = runBlocking {
        categoryDao.insertAllCategories(categories)

        val databaseCategories = categoryDao.getRandomSuggestions()

        assertThat(databaseCategories.size == 8).isTrue()
        databaseCategories.forEach { category -> assertThat(categories.contains(category)).isTrue() }
    }

    @Test
    fun shouldInsertLastSearchesListAndGetLatestSearchWordFromLastSearchesTable() = runBlocking {
        val search = SearchWordEntity(id = 7, searchWord = "search8")

        lastSearches.forEach { searchWordEntity -> searchWordDao.insertSearchWord(searchWordEntity) }

        val queryResult = searchWordDao.getLatestSearchWord()

        assertThat(queryResult == search).isTrue()
    }

    @Test
    fun shouldGetLastSearchesListInLastSearchesTable() = runBlocking {
        lastSearches.forEach { searchWordEntity -> searchWordDao.insertSearchWord(searchWordEntity) }

        val queryResult = searchWordDao.getLastSearchWordList()

        queryResult.forEach { searchWordEntity ->
            assertThat(lastSearches.contains(searchWordEntity)).isTrue()
        }
    }

    @Test
    fun shouldDeleteAnExpecifiedWorkFromLastSearchesTable() = runBlocking {
        val deletedSearch = SearchWordEntity(id = 4, searchWord = "search5")

        lastSearches.forEach { searchWordEntity -> searchWordDao.insertSearchWord(searchWordEntity) }

        searchWordDao.deleteSearchWordByWord("search5")

        val queryResult = searchWordDao.getLastSearchWordList()

        assertThat(queryResult.contains(deletedSearch)).isFalse()
    }

    @Test
    fun shouldDeleteLatestSearchFromLastSearchesTable() = runBlocking {
        val deletedSearch = SearchWordEntity(id = 0, searchWord = "search")

        lastSearches.forEach { searchWordEntity -> searchWordDao.insertSearchWord(searchWordEntity) }

        searchWordDao.deleteLastSearchWord()

        val queryResult = searchWordDao.getLastSearchWordList()

        assertThat(queryResult.contains(deletedSearch)).isFalse()
    }

    private companion object {
        val categories = listOf(
            CategoryEntity("category1"),
            CategoryEntity("category2"),
            CategoryEntity("category3"),
            CategoryEntity("category4"),
            CategoryEntity("category5"),
            CategoryEntity("category6"),
            CategoryEntity("category7"),
            CategoryEntity("category8"),
            CategoryEntity("category9"),
            CategoryEntity("category10")
        )

        val lastSearches = listOf(
            SearchWordEntity(
                id = 0,
                searchWord = "search1"
            ),
            SearchWordEntity(
                id = 1,
                searchWord = "search2"
            ),
            SearchWordEntity(
                id = 2,
                searchWord = "search3"
            ),
            SearchWordEntity(
                id = 3,
                searchWord = "search4"
            ),
            SearchWordEntity(
                id = 4,
                searchWord = "search5"
            ),
            SearchWordEntity(
                id = 5,
                searchWord = "search6"
            ),
            SearchWordEntity(
                id = 6,
                searchWord = "search7"
            ),
            SearchWordEntity(
                id = 7,
                searchWord = "search8"
            )
        )
    }
}
