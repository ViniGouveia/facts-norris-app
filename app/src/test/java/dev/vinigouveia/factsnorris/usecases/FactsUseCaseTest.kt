package dev.vinigouveia.factsnorris.usecases

import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import dev.vinigouveia.factsnorris.shared.classes.fact.FactDisplay
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FactsUseCaseTest {

    private lateinit var useCase: FactsUseCase

    private val factsRepository = mockk<FactsRepository>()
    private val searchWordRepository = mockk<SearchWordRepository>()

    @BeforeEach
    fun initialize() {
        useCase = FactsUseCaseImpl(factsRepository, searchWordRepository)
    }

    @Test
    fun `should fetch facts successfully`() = runBlocking {
        val response = listOf(
            Fact("id", null, "url", "description"),
            Fact("id", null, "url", "description")
        )

        coEvery { factsRepository.fetchFacts(any()) } returns response

        Assertions.assertEquals(response, useCase.fetchFacts(""))

        coVerify(exactly = 1) {
            factsRepository.fetchFacts("")
        }

        confirmVerified(factsRepository)
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

        Assertions.assertEquals(displayList, useCase.getMappedFactsList(facts))
    }

    @Test
    fun `should get latest search word successfully`() = runBlocking {
        val response = "lastSearch"

        coEvery { searchWordRepository.getLatestSearchWord() } returns response

        Assertions.assertEquals(response, useCase.getLatestSearchWord())

        coVerify(exactly = 1) {
            searchWordRepository.getLatestSearchWord()
        }

        confirmVerified(searchWordRepository)
    }

}
