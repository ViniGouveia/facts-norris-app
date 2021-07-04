package dev.vinigouveia.factsnorris.viewmodel

import dev.vinigouveia.factsnorris.TestCoroutineRule
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyCategoriesListReturnedException
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.usecases.AreCategoriesSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.DeleteLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsLastSearchesListFullUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsSearchWordSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveExistingSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCase
import dev.vinigouveia.factsnorris.ui.search.SearchContract
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

    private lateinit var viewModel: SearchContract.ViewModel

    private val navigator = mockk<Navigator>()
    private val errorHandler = mockk<ErrorHandler>()
    private val fetchCategoriesUseCase = mockk<FetchCategoriesUseCase>()
    private val saveCategoriesUseCase = mockk<SaveCategoriesUseCase>()
    private val getLastSearchWordListUseCase = mockk<GetLastSearchWordListUseCase>()
    private val getRandomSuggestionsUseCase = mockk<GetRandomSuggestionsUseCase>()
    private val saveSearchWordUseCase = mockk<SaveSearchWordUseCase>()
    private val deleteLastSearchWordUseCase = mockk<DeleteLastSearchWordUseCase>()
    private val isLastSearchesListFullUseCase = mockk<IsLastSearchesListFullUseCase>()
    private val areCategoriesSavedUseCase = mockk<AreCategoriesSavedUseCase>()
    private val isSearchWordSavedUseCase = mockk<IsSearchWordSavedUseCase>()
    private val saveExistingSearchWordUseCase = mockk<SaveExistingSearchWordUseCase>()

    @Rule
    val testCoroutineRule = TestCoroutineRule()

    @BeforeEach
    fun initialize() {
        viewModel = SearchViewModel(
            navigator,
            errorHandler,
            fetchCategoriesUseCase,
            saveCategoriesUseCase,
            getLastSearchWordListUseCase,
            getRandomSuggestionsUseCase,
            saveSearchWordUseCase,
            deleteLastSearchWordUseCase,
            isLastSearchesListFullUseCase,
            areCategoriesSavedUseCase,
            isSearchWordSavedUseCase,
            saveExistingSearchWordUseCase
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

            coEvery { areCategoriesSavedUseCase.areSavedCategories() } returns false
            coEvery { fetchCategoriesUseCase.fetchCategories() } returns response
            coEvery { saveCategoriesUseCase.saveCategories(any()) } just Runs
            coEvery { getLastSearchWordListUseCase.getLastSearchWordList() } returns lastSearches

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                fetchCategoriesUseCase.fetchCategories()
                saveCategoriesUseCase.saveCategories(response)
                getLastSearchWordListUseCase.getLastSearchWordList()
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
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

            coEvery { areCategoriesSavedUseCase.areSavedCategories() } returns true
            coEvery { getRandomSuggestionsUseCase.getRandomSuggestions() } returns suggestions
            coEvery { getLastSearchWordListUseCase.getLastSearchWordList() } returns lastSearches

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                getRandomSuggestionsUseCase.getRandomSuggestions()
                getLastSearchWordListUseCase.getLastSearchWordList()
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
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

            coEvery { areCategoriesSavedUseCase.areSavedCategories() } returns false
            coEvery { fetchCategoriesUseCase.fetchCategories() } returns response
            coEvery { saveCategoriesUseCase.saveCategories(any()) } just Runs
            coEvery { getLastSearchWordListUseCase.getLastSearchWordList() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns "error"

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                fetchCategoriesUseCase.fetchCategories()
                saveCategoriesUseCase.saveCategories(response)
                getLastSearchWordListUseCase.getLastSearchWordList()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
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

            coEvery { areCategoriesSavedUseCase.areSavedCategories() } returns false
            coEvery { fetchCategoriesUseCase.fetchCategories() } returns response
            coEvery { saveCategoriesUseCase.saveCategories(any()) } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns "error"

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                fetchCategoriesUseCase.fetchCategories()
                saveCategoriesUseCase.saveCategories(response)
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should throws exception when fetching categories`() =
        testCoroutineRule.runBlockingTest {
            coEvery { areCategoriesSavedUseCase.areSavedCategories() } returns false
            coEvery { fetchCategoriesUseCase.fetchCategories() } throws EmptyCategoriesListReturnedException
            coEvery { errorHandler.getErrorMessage(any()) } returns "error"

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                fetchCategoriesUseCase.fetchCategories()
                errorHandler.getErrorMessage(EmptyCategoriesListReturnedException)
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should throws exception when verifying if has categories saved`() =
        testCoroutineRule.runBlockingTest {
            val error = Exception()

            coEvery { areCategoriesSavedUseCase.areSavedCategories() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } throws error

            viewModel.getSuggestionsAndLastSearches()

            coVerify(exactly = 1) {
                areCategoriesSavedUseCase.areSavedCategories()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should save new search word and pop back, if last searches are not full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are categories saved and get random suggestions successfully`()

            coEvery { isLastSearchesListFullUseCase.isLastSearchesListFull(any()) } returns false
            coEvery { isSearchWordSavedUseCase.isSearchWordSaved(any()) } returns false
            coEvery { saveSearchWordUseCase.saveSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search2")

            coVerify(exactly = 1) {
                isLastSearchesListFullUseCase.isLastSearchesListFull(1)
                isSearchWordSavedUseCase.isSearchWordSaved("search2")
                saveSearchWordUseCase.saveSearchWord("search2")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should save existing search word and pop back, if last searches are not full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are categories saved and get random suggestions successfully`()

            coEvery { isLastSearchesListFullUseCase.isLastSearchesListFull(any()) } returns false
            coEvery { isSearchWordSavedUseCase.isSearchWordSaved(any()) } returns true
            coEvery { saveExistingSearchWordUseCase.saveExistingSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search")

            coVerify(exactly = 1) {
                isLastSearchesListFullUseCase.isLastSearchesListFull(1)
                isSearchWordSavedUseCase.isSearchWordSaved("search")
                saveExistingSearchWordUseCase.saveExistingSearchWord("search")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should save new search word and pop back, if last searches are full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are no categories saved, fetch categories and then get last searches successfully`()

            coEvery { isLastSearchesListFullUseCase.isLastSearchesListFull(any()) } returns true
            coEvery { isSearchWordSavedUseCase.isSearchWordSaved(any()) } returns false
            coEvery { deleteLastSearchWordUseCase.deleteLastSearchWord() } just Runs
            coEvery { saveSearchWordUseCase.saveSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search9")

            coVerify(exactly = 1) {
                isLastSearchesListFullUseCase.isLastSearchesListFull(8)
                isSearchWordSavedUseCase.isSearchWordSaved("search9")
                deleteLastSearchWordUseCase.deleteLastSearchWord()
                saveSearchWordUseCase.saveSearchWord("search9")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should save existing search word and pop back, if last searches are full`() =
        testCoroutineRule.runBlockingTest {
            `should verify that are no categories saved, fetch categories and then get last searches successfully`()

            coEvery { isLastSearchesListFullUseCase.isLastSearchesListFull(any()) } returns true
            coEvery { isSearchWordSavedUseCase.isSearchWordSaved(any()) } returns true
            coEvery { deleteLastSearchWordUseCase.deleteLastSearchWord() } just Runs
            coEvery { saveExistingSearchWordUseCase.saveExistingSearchWord(any()) } just Runs
            every { navigator.popBack() } just Runs

            viewModel.saveSearchWordAndReturn("search1")

            coVerify(exactly = 1) {
                isLastSearchesListFullUseCase.isLastSearchesListFull(8)
                isSearchWordSavedUseCase.isSearchWordSaved("search1")
                deleteLastSearchWordUseCase.deleteLastSearchWord()
                saveExistingSearchWordUseCase.saveExistingSearchWord("search1")
            }

            verify(exactly = 1) { navigator.popBack() }

            confirmVerified(
                navigator,
                errorHandler,
                fetchCategoriesUseCase,
                saveCategoriesUseCase,
                getLastSearchWordListUseCase,
                getRandomSuggestionsUseCase,
                saveSearchWordUseCase,
                deleteLastSearchWordUseCase,
                isLastSearchesListFullUseCase,
                areCategoriesSavedUseCase,
                isSearchWordSavedUseCase,
                saveExistingSearchWordUseCase
            )
        }

    @Test
    fun `should pop back`() {
        every { navigator.popBack() } just Runs

        viewModel.onBackPressed()

        verify(exactly = 1) { navigator.popBack() }

        confirmVerified(
            navigator,
            errorHandler,
            fetchCategoriesUseCase,
            saveCategoriesUseCase,
            getLastSearchWordListUseCase,
            getRandomSuggestionsUseCase,
            saveSearchWordUseCase,
            deleteLastSearchWordUseCase,
            isLastSearchesListFullUseCase,
            areCategoriesSavedUseCase,
            isSearchWordSavedUseCase,
            saveExistingSearchWordUseCase
        )
    }
}
