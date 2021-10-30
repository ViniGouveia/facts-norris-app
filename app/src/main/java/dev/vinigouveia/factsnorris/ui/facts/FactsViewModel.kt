package dev.vinigouveia.factsnorris.ui.facts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.classes.FactState
import dev.vinigouveia.factsnorris.shared.classes.FactState.Companion.emptyState
import dev.vinigouveia.factsnorris.shared.classes.FactState.Companion.errorState
import dev.vinigouveia.factsnorris.shared.classes.FactState.Companion.firstAccessState
import dev.vinigouveia.factsnorris.shared.classes.FactState.Companion.loadingState
import dev.vinigouveia.factsnorris.shared.classes.FactState.Companion.successState
import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactsViewModel(
    private val navigator: Navigator,
    private val errorHandler: ErrorHandler,
    private val factsUseCase: FactsUseCase
) : ViewModel() {

    val factState = MutableLiveData<FactState>()

    private var facts: List<Fact> = listOf()

    fun getLastSearchWordAndFetchFacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                factState.postValue(loadingState())

                val newSearchWord = factsUseCase.getLatestSearchWord()

                if (newSearchWord.isNullOrEmpty()) {
                    factState.postValue(firstAccessState())
                    return@launch
                }

                facts = factsUseCase.fetchFacts(newSearchWord)

                factState.postValue(
                    if (facts.isEmpty()) emptyState()
                    else successState(
                        factsUseCase.getMappedFactsList(facts),
                        newSearchWord
                    )
                )
            } catch (error: Exception) {
                facts = listOf()
                factState.postValue(errorState(errorHandler.getErrorMessage(error)))
            }
        }
    }

    fun navigateToShareFragment() =
        navigator.navigate(R.id.navigate_to_search_from_facts)

    fun shareFact(id: String) =
        facts.first { it.id == id }
}
