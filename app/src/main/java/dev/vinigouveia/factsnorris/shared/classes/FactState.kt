package dev.vinigouveia.factsnorris.shared.classes

import androidx.annotation.StringRes
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.classes.fact.FactDisplay

class FactState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val facts: List<FactDisplay> = listOf(),
    val quantity: String? = null,
    val searchWord: String? = null,
    @StringRes val errorMessage: Int = R.string.empty_string
) {
    companion object {
        fun loadingState() = FactState(isLoading = true)

        fun errorState(@StringRes errorMessageResult: Int) = FactState(
            hasError = true,
            errorMessage = errorMessageResult
        )

        fun emptyState() = FactState(
            hasError = true,
            errorMessage = R.string.empty_list_error_message
        )

        fun firstAccessState() = FactState(
            hasError = true,
            errorMessage = R.string.first_access_message
        )

        fun successState(factsResult: List<FactDisplay>, searchWord: String) = FactState(
            isLoading = false,
            hasError = false,
            facts = factsResult,
            quantity = factsResult.size.toString(),
            searchWord = searchWord
        )
    }
}
