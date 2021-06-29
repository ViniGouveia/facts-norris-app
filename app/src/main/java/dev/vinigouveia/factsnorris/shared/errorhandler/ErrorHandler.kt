package dev.vinigouveia.factsnorris.shared.errorhandler

import android.content.Context
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCaseImpl
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
            is GetLastSearchWordUseCaseImpl.SearchTermNotFoundException -> context.getString(R.string.first_access_message)
            is FetchFactsUseCaseImpl.EmptyListReturnedException -> context.getString(R.string.empty_list_error_message)
            else -> context.getString(R.string.default_error_message)
        }
}
