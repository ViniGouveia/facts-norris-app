package dev.vinigouveia.factsnorris.errorhandler

import android.content.Context
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandlerImpl
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyCategoriesListReturnedException
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyFactsListReturnedException
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.SearchTermNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
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

    private val context = mockk<Context>()

    @BeforeEach
    fun initialize() {
        errorHandler = ErrorHandlerImpl(context)
    }


    @Test
    fun `should return default error message`() = runBlocking {
        coEvery { context.getString(any()) } returns "error message"

        assertEquals("error message", errorHandler.getErrorMessage(Exception()))

        coVerify(exactly = 1) { context.getString(R.string.default_error_message) }

        confirmVerified(context)
    }

    @Test
    fun `should return EmptyCategoriesListReturnedException error message`() = runBlocking {
        coEvery { context.getString(any()) } returns "error message"

        assertEquals("error message", errorHandler.getErrorMessage(EmptyCategoriesListReturnedException))

        coVerify(exactly = 1) { context.getString(R.string.search_no_suggestions_error) }

        confirmVerified(context)
    }

    @Test
    fun `should return EmptyFactsListReturnedException error message`() = runBlocking {
        coEvery { context.getString(any()) } returns "error message"

        assertEquals("error message", errorHandler.getErrorMessage(EmptyFactsListReturnedException))

        coVerify(exactly = 1) { context.getString(R.string.empty_list_error_message) }

        confirmVerified(context)
    }

    @Test
    fun `should return SearchTermNotFoundException error message`() = runBlocking {
        coEvery { context.getString(any()) } returns "error message"

        assertEquals("error message", errorHandler.getErrorMessage(SearchTermNotFoundException))

        coVerify(exactly = 1) { context.getString(R.string.first_access_message) }

        confirmVerified(context)
    }

    @Test
    fun `should return IOException error message`() = runBlocking {
        coEvery { context.getString(any()) } returns "error message"

        assertEquals("error message", errorHandler.getErrorMessage(IOException()))

        coVerify(exactly = 1) { context.getString(R.string.no_connection_error_message) }

        confirmVerified(context)
    }
}
