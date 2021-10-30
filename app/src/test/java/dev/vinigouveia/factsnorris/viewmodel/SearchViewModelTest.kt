package dev.vinigouveia.factsnorris.viewmodel

import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.TestCoroutineRule
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCase
import dev.vinigouveia.factsnorris.ui.search.SearchViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private val navigator = mockk<Navigator>()
    private val errorHandler = mockk<ErrorHandler>()
    private val searchUseCase = mockk<SearchUseCase>()

    @Rule
    val testCoroutineRule = TestCoroutineRule()

    @BeforeEach
    fun initialize() {
        viewModel = SearchViewModel(
            navigator,
            errorHandler,
            searchUseCase
        )
    }

    @Test
    fun `should verify that are no categories saved, fetch categories and then get last searches successfully`() =
        testCoroutineRule.runBlockingTest {
            val response = listOf(
                "category1",
                "category2",
                "category3",
                "category4",
                "category5",
                "category6",
                "category7",
                "category8",
                "category9"
            )

            val lastSearches = listOf(
                "search1",
                "search2",
                "search3",
                "search4",
                "search5",
                "search6",
                "search7",
                "search8"
            )

            coEvery { searchUseCase.areSavedCategories() } returns false
            coEvery { searchUseCase.fetchCategories() } returns response
            coEvery { searchUseCase.saveCategories(any()) } just Runs
            coEvery { searchUseCase.getLastSearchWordList() } returns lastSearches

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                searchUseCase.fetchCategories()
                searchUseCase.saveCategories(response)
                searchUseCase.getLastSearchWordList()
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should verify that are categories saved and get random suggestions successfully`() =
        testCoroutineRule.runBlockingTest {
            val suggestions = listOf(
                "category8",
                "category7",
                "category3",
                "category4",
                "category2",
                "category6",
                "category1",
                "category5"
            )

            val lastSearches = listOf("search")

            coEvery { searchUseCase.areSavedCategories() } returns true
            coEvery { searchUseCase.getRandomSuggestions() } returns suggestions
            coEvery { searchUseCase.getLastSearchWordList() } returns lastSearches

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                searchUseCase.getRandomSuggestions()
                searchUseCase.getLastSearchWordList()
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should throws exception when getting last searches list`() =
        testCoroutineRule.runBlockingTest {
            val response = listOf(
                "category1",
                "category2",
                "category3",
                "category4",
                "category5",
                "category6",
                "category7",
                "category8",
                "category9"
            )

            val error = Exception()

            coEvery { searchUseCase.areSavedCategories() } returns false
            coEvery { searchUseCase.fetchCategories() } returns response
            coEvery { searchUseCase.saveCategories(any()) } just Runs
            coEvery { searchUseCase.getLastSearchWordList() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                searchUseCase.fetchCategories()
                searchUseCase.saveCategories(response)
                searchUseCase.getLastSearchWordList()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should throws exception when saving categories`() =
        testCoroutineRule.runBlockingTest {
            val response = listOf(
                "category1",
                "category2",
                "category3",
                "category4",
                "category5",
                "category6",
                "category7",
                "category8",
                "category9"
            )

            val error = Exception()

            coEvery { searchUseCase.areSavedCategories() } returns false
            coEvery { searchUseCase.fetchCategories() } returns response
            coEvery { searchUseCase.saveCategories(any()) } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                searchUseCase.fetchCategories()
                searchUseCase.saveCategories(response)
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should throws exception when fetching categories`() =
        testCoroutineRule.runBlockingTest {
            val error = Exception()
            coEvery { searchUseCase.areSavedCategories() } returns false
            coEvery { searchUseCase.fetchCategories() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                searchUseCase.fetchCategories()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should throws exception when verifying if has categories saved`() =
        testCoroutineRule.runBlockingTest {
            val error = Exception()

            coEvery { searchUseCase.areSavedCategories() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                searchUseCase.areSavedCategories()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should save new search word and pop back, if last searches are not full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are categories saved and get random suggestions successfully`()

            coEvery { searchUseCase.isLastSearchesListFull(any()) } returns false
            coEvery { searchUseCase.isSearchWordSaved(any()) } returns false
            coEvery { searchUseCase.saveSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search2")

            coVerify(exactly = 1) {
                searchUseCase.isLastSearchesListFull(1)
                searchUseCase.isSearchWordSaved("search2")
                searchUseCase.saveSearchWord("search2")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should save existing search word and pop back, if last searches are not full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are categories saved and get random suggestions successfully`()

            coEvery { searchUseCase.isLastSearchesListFull(any()) } returns false
            coEvery { searchUseCase.isSearchWordSaved(any()) } returns true
            coEvery { searchUseCase.saveExistingSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search")

            coVerify(exactly = 1) {
                searchUseCase.isLastSearchesListFull(1)
                searchUseCase.isSearchWordSaved("search")
                searchUseCase.saveExistingSearchWord("search")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should save new search word and pop back, if last searches are full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are no categories saved, fetch categories and then get last searches successfully`()

            coEvery { searchUseCase.isLastSearchesListFull(any()) } returns true
            coEvery { searchUseCase.isSearchWordSaved(any()) } returns false
            coEvery { searchUseCase.deleteLastSearchWord() } just Runs
            coEvery { searchUseCase.saveSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search9")

            coVerify(exactly = 1) {
                searchUseCase.isLastSearchesListFull(8)
                searchUseCase.isSearchWordSaved("search9")
                searchUseCase.deleteLastSearchWord()
                searchUseCase.saveSearchWord("search9")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should save existing search word and pop back, if last searches are full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are no categories saved, fetch categories and then get last searches successfully`()

            coEvery { searchUseCase.isLastSearchesListFull(any()) } returns true
            coEvery { searchUseCase.isSearchWordSaved(any()) } returns true
            coEvery { searchUseCase.saveExistingSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search1")

            coVerify(exactly = 1) {
                searchUseCase.isLastSearchesListFull(8)
                searchUseCase.isSearchWordSaved("search1")
                searchUseCase.saveExistingSearchWord("search1")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(navigator, errorHandler, searchUseCase)
        }

    @Test
    fun `should pop back`() {
        every { navigator.popBack() } just Runs

        viewModel.onBackPressed()

        verify(exactly = 1) { navigator.popBack() }

        confirmVerified(navigator, errorHandler, searchUseCase)
    }
}
