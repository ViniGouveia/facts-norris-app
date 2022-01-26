package dev.vinigouveia.factsnorris.shared.errorhandler

import dev.vinigouveia.factsnorris.R
import java.io.IOException

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

interface ErrorHandler {
    suspend fun getErrorMessage(error: Exception): Int
}

class ErrorHandlerImpl : ErrorHandler {
    override suspend fun getErrorMessage(error: Exception): Int =
        when (error) {
            is IOException -> R.string.no_connection_error_message
            else -> R.string.default_error_message
        }
}
