package dev.vinigouveia.factsnorris.shared.errorhandler

import android.content.Context
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyCategoriesListReturnedException
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyFactsListReturnedException
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.SearchTermNotFoundException
import java.io.IOException

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

interface ErrorHandler {
    suspend fun getErrorMessage(error: Exception): String
}

class ErrorHandlerImpl(
    private val context: Context
) : ErrorHandler {
    override suspend fun getErrorMessage(error: Exception): String =
        when (error) {
            is IOException -> context.getString(R.string.no_connection_error_message)
            is SearchTermNotFoundException -> context.getString(R.string.first_access_message)
            is EmptyFactsListReturnedException -> context.getString(R.string.empty_list_error_message)
            is EmptyCategoriesListReturnedException -> context.getString(R.string.search_no_suggestions_error)
            else -> context.getString(R.string.default_error_message)
        }
}
