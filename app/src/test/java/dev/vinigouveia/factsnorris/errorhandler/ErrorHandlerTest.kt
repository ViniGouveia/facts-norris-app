package dev.vinigouveia.factsnorris.errorhandler

import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandlerImpl
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class ErrorHandlerTest {

    private lateinit var errorHandler: ErrorHandler

    @BeforeEach
    fun initialize() {
        errorHandler = ErrorHandlerImpl()
    }

    @Test
    fun `should return default error message`() = runBlocking {
        assertEquals(R.string.default_error_message, errorHandler.getErrorMessage(Exception()))
    }

    @Test
    fun `should return IOException error message`() = runBlocking {
        assertEquals(
            R.string.no_connection_error_message,
            errorHandler.getErrorMessage(IOException())
        )
    }
}
