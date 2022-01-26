package dev.vinigouveia.factsnorris.shared.classes

import androidx.annotation.StringRes
import dev.vinigouveia.factsnorris.R

class SearchState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val suggestionsList: List<String> = listOf(),
    val lastSearchesList: List<String> = listOf(),
    @StringRes val errorMessage: Int = R.string.empty_string
) {
    companion object {
        fun loadingState() = SearchState(isLoading = true)

        fun hasErrorState(@StringRes errorMessageResult: Int) = SearchState(
            hasError = true,
            errorMessage = errorMessageResult
        )

        fun emptyState() = SearchState(
            hasError = true,
            errorMessage = R.string.search_no_suggestions_error
        )

        fun successState(
            suggestionsListResult: List<String>,
            lastSearchesListResult: List<String>
        ) = SearchState(
            suggestionsList = suggestionsListResult,
            lastSearchesList = lastSearchesListResult
        )
    }
}
