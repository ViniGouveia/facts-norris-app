package dev.vinigouveia.factsnorris.ui.facts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.data.FactDisplay
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.threadprovider.ThreadProvider
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCase
import kotlinx.coroutines.launch

class FactsViewModel(
    private val navigator: Navigator,
    private val threadProvider: ThreadProvider,
    private val errorHandler: ErrorHandler,
    private val fetchFactsUseCase: FetchFactsUseCase,
    private val getLastSearchWordUseCase: GetLastSearchWordUseCase,
    private val getMappedFactsListUseCase: GetMappedFactsListUseCase
) : ViewModel(), FactsContract.ViewModel {

    val factsList = MutableLiveData<List<FactDisplay>>()

    val errorMessage = MutableLiveData<String>()
    val searchWord = MutableLiveData<String>()
    val quantity = MutableLiveData<String>()

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()

    private lateinit var facts: List<Fact>

    override fun getLastSearchWordAndFetchFacts() {
        viewModelScope.launch(threadProvider.io) {
            try {
                val newSearchWord = getLastSearchWordUseCase.getLastSearchWord()
                searchWord.postValue(newSearchWord)

                loadingState.postValue(true)

                facts = fetchFactsUseCase.fetchFacts(newSearchWord)
                factsList.postValue(getMappedFactsListUseCase.getMappedFactsList(facts))
                quantity.postValue(facts.size.toString())

                loadingState.postValue(false)
                errorState.postValue(false)
            } catch (@Suppress("TooGenericExceptionCaught") error: Exception) {
                loadingState.postValue(false)
                errorMessage.postValue(errorHandler.getErrorMessage(error))
                errorState.postValue(true)
            }
        }
    }

    override fun navigateToShareFragment() =
        navigator.navigate(R.id.navigate_to_search_from_facts)

    override fun shareFact(id: String) =
        facts.first { it.id == id }
}
