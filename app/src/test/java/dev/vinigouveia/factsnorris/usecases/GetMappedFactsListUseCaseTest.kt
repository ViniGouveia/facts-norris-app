package dev.vinigouveia.factsnorris.usecases

import android.content.Context
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.data.FactDisplay
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
class GetMappedFactsListUseCaseTest {

    private lateinit var useCase: GetMappedFactsListUseCase

    private val context = mockk<Context>()

    @BeforeEach
    fun initialize() {
        useCase = GetMappedFactsListUseCaseImpl(context)
    }

    @Test
    fun `should return formatted facts list with all variants`() = runBlocking {
        val facts = listOf(
            Fact(
                id = "id",
                category = null,
                url = "url",
                description = "description"
            ),
            Fact(
                id = "id",
                category = "category",
                url = "url",
                description = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234"
            ),
            Fact(
                id = "id",
                category = null,
                url = "url",
                description = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234"
            ),
            Fact(
                id = "id",
                category = "category",
                url = "url",
                description = "description"
            )
        )

        val displayList = listOf(
            FactDisplay(
                id = "id",
                category = "Uncategorized",
                fontSize = R.dimen.text_large,
                description = "description"
            ),
            FactDisplay(
                id = "id",
                category = "Category",
                fontSize = R.dimen.text_medium,
                description = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234"
            ),
            FactDisplay(
                id = "id",
                category = "Uncategorized",
                fontSize = R.dimen.text_medium,
                description = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234"
            ),
            FactDisplay(
                id = "id",
                category = "Category",
                fontSize = R.dimen.text_large,
                description = "description"
            )
        )

        coEvery { context.getString(any()) } returns "Uncategorized"

        assertEquals(displayList, useCase.getMappedFactsList(facts))

        coVerify(exactly = 2) { context.getString(R.string.fact_uncategorized) }

        confirmVerified(context)
    }
}
