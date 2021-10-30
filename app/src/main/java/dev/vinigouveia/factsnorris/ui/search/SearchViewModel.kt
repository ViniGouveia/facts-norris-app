package dev.vinigouveia.factsnorris.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vinigouveia.factsnorris.shared.classes.SearchState
import dev.vinigouveia.factsnorris.shared.classes.SearchState.Companion.emptyState
import dev.vinigouveia.factsnorris.shared.classes.SearchState.Companion.hasErrorState
import dev.vinigouveia.factsnorris.shared.classes.SearchState.Companion.loadingState
import dev.vinigouveia.factsnorris.shared.classes.SearchState.Companion.successState
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

@Suppress("LongParameterList")
class SearchViewModel(
    private val navigator: Navigator,
    private val errorHandler: ErrorHandler,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    val searchState = MutableLiveData<SearchState>()

    private lateinit var lastSearches: List<String>

    fun getSuggestionsAndLastSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val areCategoriesSaved = searchUseCase.areSavedCategories()

                val savedCategories = if (areCategoriesSaved) {
                    searchUseCase.getRandomSuggestions()
                } else {
                    searchState.postValue(loadingState())

                    val categories = searchUseCase.fetchCategories()

                    if (categories.isEmpty()) {
                        searchState.postValue(emptyState())
                        return@launch
                    }

                    searchUseCase.saveCategories(categories)

                    categories.shuffled().subList(FIRST_INDEX, LAST_INDEX)
                }

                lastSearches = searchUseCase.getLastSearchWordList()

                searchState.postValue(successState(savedCategories, lastSearches))
            } catch (error: Exception) {
                hasErrorState(errorHandler.getErrorMessage(error))
            }
        }
    }

    fun saveSearchWordAndReturn(searchWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val isLastSearchesFull =
                searchUseCase.isLastSearchesListFull(lastSearches.size)

            val doesSearchWordExists = searchUseCase.isSearchWordSaved(searchWord)

            if (isLastSearchesFull) {
                if (doesSearchWordExists) searchUseCase.saveExistingSearchWord(searchWord)
                else {
                    searchUseCase.deleteLastSearchWord()
                    searchUseCase.saveSearchWord(searchWord)
                }
            } else {
                if (doesSearchWordExists) searchUseCase.saveExistingSearchWord(searchWord)
                else searchUseCase.saveSearchWord(searchWord)
            }
        }

        navigator.popBack()
    }

    fun onBackPressed() {
        navigator.popBack()
    }

    private companion object {
        const val FIRST_INDEX = 0
        const val LAST_INDEX = 7
    }
}
