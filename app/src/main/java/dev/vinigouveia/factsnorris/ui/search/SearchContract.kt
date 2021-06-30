package dev.vinigouveia.factsnorris.ui.search

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

interface SearchContract {
    interface ViewModel {
        fun getSuggestionsAndLastSearches()
        fun saveSearchWordAndReturn(searchWord: String)
        fun onBackPressed()
    }
}
