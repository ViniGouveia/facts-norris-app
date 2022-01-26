package dev.vinigouveia.factsnorris.viewmodel

import com.google.common.truth.Truth.assertThat
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.TestCoroutineRule
import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import dev.vinigouveia.factsnorris.shared.classes.fact.FactDisplay
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCase
import dev.vinigouveia.factsnorris.ui.facts.FactsViewModel
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
class FactsViewModelTest {

    private lateinit var viewModel: FactsViewModel

    @Rule
    val testCoroutineRule = TestCoroutineRule()

    private val navigator = mockk<Navigator>()
    private val errorHandler = mockk<ErrorHandler>()
    private val factsUseCase = mockk<FactsUseCase>()

    @BeforeEach
    fun initialize() {
        viewModel = FactsViewModel(
            navigator,
            errorHandler,
            factsUseCase
        )
    }

    @Test
    fun `should get latest search word and search facts successfully`() =
        testCoroutineRule.runBlockingTest {
            val response = listOf(
                Fact("id", "category", "url", "description"),
                Fact("id2", null, "url", "description"),
                Fact("id3", "category", "url", "description")
            )

            val display = listOf(
                FactDisplay("id", "Category", "description", R.dimen.text_large),
                FactDisplay("id2", "Uncategorized", "description", R.dimen.text_large),
                FactDisplay("id3", "Category", "description", R.dimen.text_large)
            )

            coEvery { factsUseCase.getLatestSearchWord() } returns "search"
            coEvery { factsUseCase.fetchFacts(any()) } returns response
            coEvery { factsUseCase.getMappedFactsList(any()) } returns display

            viewModel.getLastSearchWordAndFetchFacts()

            coVerify(exactly = 1) {
                with(factsUseCase) {
                    getLatestSearchWord()
                    fetchFacts("search")
                    getMappedFactsList(response)
                }
            }

            confirmVerified(navigator, errorHandler, factsUseCase)
        }

    @Test
    fun `should let error state when getting latest search word`() =
        testCoroutineRule.runBlockingTest {
            val error = Exception()
            coEvery { factsUseCase.getLatestSearchWord() } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getLastSearchWordAndFetchFacts()

            coVerify(exactly = 1) {
                factsUseCase.getLatestSearchWord()
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, factsUseCase)
        }

    @Test
    fun `should let empty state when fetching facts`() =
        testCoroutineRule.runBlockingTest {
            coEvery { factsUseCase.getLatestSearchWord() } returns "search"
            coEvery { factsUseCase.fetchFacts(any()) } returns listOf()

            viewModel.getLastSearchWordAndFetchFacts()

            coVerify(exactly = 1) {
                with(factsUseCase) {
                    getLatestSearchWord()
                    fetchFacts("search")
                }
            }

            confirmVerified(navigator, errorHandler, factsUseCase)
        }

    @Test
    fun `should let error state when getting fetching facts`() =
        testCoroutineRule.runBlockingTest {
            val error = Exception()
            coEvery { factsUseCase.getLatestSearchWord() } returns "search"
            coEvery { factsUseCase.fetchFacts(any()) } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getLastSearchWordAndFetchFacts()

            coVerify(exactly = 1) {
                with(factsUseCase) {
                    getLatestSearchWord()
                    fetchFacts("search")
                }
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, factsUseCase)
        }

    @Test
    fun `should let error state when getting mapped facts list`() =
        testCoroutineRule.runBlockingTest {
            val response = listOf(
                Fact("id", "category", "url", "description"),
                Fact("id2", null, "url", "description"),
                Fact("id3", "category", "url", "description")
            )
            val error = Exception()
            coEvery { factsUseCase.getLatestSearchWord() } returns "search"
            coEvery { factsUseCase.fetchFacts(any()) } returns response
            coEvery { factsUseCase.getMappedFactsList(any()) } throws error
            coEvery { errorHandler.getErrorMessage(any()) } returns R.string.default_error_message

            viewModel.getLastSearchWordAndFetchFacts()

            coVerify(exactly = 1) {
                with(factsUseCase) {
                    getLatestSearchWord()
                    fetchFacts("search")
                    getMappedFactsList(response)
                }
                errorHandler.getErrorMessage(error)
            }

            confirmVerified(navigator, errorHandler, factsUseCase)
        }

    @Test
    fun `should navigate to search successfully`() {
        every { navigator.navigate(any()) } just Runs

        viewModel.navigateToShareFragment()

        verify(exactly = 1) { navigator.navigate(R.id.navigate_to_search_from_facts) }

        confirmVerified(navigator, errorHandler, factsUseCase)
    }

    @Test
    fun `should get shareable fact successfully`() {
        `should get latest search word and search facts successfully`()

        val fact = Fact("id", "category", "url", "description")

        assertThat(viewModel.shareFact("id")).isEqualTo(fact)

        confirmVerified(navigator, errorHandler, factsUseCase)
    }
}
