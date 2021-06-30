package dev.vinigouveia.factsnorris.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.threadprovider.ThreadProvider
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
import kotlinx.coroutines.launch

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

class SearchViewModel(
    private val navigator: Navigator,
    private val threadProvider: ThreadProvider,
    private val errorHandler: ErrorHandler,
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
    private val saveCategoriesUseCase: SaveCategoriesUseCase,
    private val getLastSearchWordListUseCase: GetLastSearchWordListUseCase,
    private val getRandomSuggestionsUseCase: GetRandomSuggestionsUseCase,
    private val saveSearchWordUseCase: SaveSearchWordUseCase,
    private val deleteLastSearchWordUseCase: DeleteLastSearchWordUseCase,
    private val isLastSearchesListFullUseCase: IsLastSearchesListFullUseCase,
    private val areCategoriesSavedUseCase: AreCategoriesSavedUseCase,
    private val isSearchWordSavedUseCase: IsSearchWordSavedUseCase,
    private val saveExistingSearchWordUseCase: SaveExistingSearchWordUseCase
) : ViewModel(), SearchContract.ViewModel {

    val suggestionsList = MutableLiveData<List<String>>()
    val lastSearchesList = MutableLiveData<List<String>>()

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String>()

    private lateinit var lastSearches: List<String>

    override fun getSuggestionsAndLastSearches() {
        viewModelScope.launch(threadProvider.io) {
            try {
                val areCategoriesSaved = areCategoriesSavedUseCase.areSavedCategories()

                val savedCategories = if (areCategoriesSaved) {
                    getRandomSuggestionsUseCase.getRandomSuggestions()
                } else {
                    loadingState.postValue(true)

                    val categories = fetchCategoriesUseCase.fetchCategories()
                    saveCategoriesUseCase.saveCategories(categories)

                    loadingState.postValue(false)
                    errorState.postValue(false)

                    categories.shuffled().subList(FIRST_INDEX, LAST_INDEX)
                }

                lastSearches = getLastSearchWordListUseCase.getLastSearchWordList()

                lastSearchesList.postValue(lastSearches)
                suggestionsList.postValue(savedCategories)
            } catch (@Suppress("TooGenericExceptionTooCaught") error: Exception) {
                loadingState.postValue(false)
                errorMessage.postValue(errorHandler.getErrorMessage(error))
                errorState.postValue(true)
            }
        }
    }

    override fun saveSearchWordAndReturn(searchWord: String) {
        viewModelScope.launch(threadProvider.io) {
            val isLastSearchesFull =
                isLastSearchesListFullUseCase.isLastSearchesListFull(lastSearches.size)

            val doesSearchWordExists = isSearchWordSavedUseCase.isSearchWordSaved(searchWord)

            if (isLastSearchesFull) {
                deleteLastSearchWordUseCase.deleteLastSearchWord()
            }

            if (doesSearchWordExists) {
                saveExistingSearchWordUseCase.saveExistingSearchWord(searchWord)
            } else saveSearchWordUseCase.saveSearchWord(searchWord)
        }

        navigator.popBack()
    }

    override fun onBackPressed() {
        navigator.popBack()
    }

    private companion object {
        const val FIRST_INDEX = 0
        const val LAST_INDEX = 7
    }
}
