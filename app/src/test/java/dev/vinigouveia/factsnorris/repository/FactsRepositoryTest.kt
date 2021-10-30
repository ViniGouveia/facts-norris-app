package dev.vinigouveia.factsnorris.repository

import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import dev.vinigouveia.factsnorris.shared.classes.response.FactResponse
import dev.vinigouveia.factsnorris.shared.classes.response.FactsListResponse
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepositoryImpl
import dev.vinigouveia.factsnorris.shared.service.FactsService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class FactsRepositoryTest {

    private lateinit var repository: FactsRepository

    private val service = mockk<FactsService>()

    @BeforeEach
    fun initialize() {
        repository = FactsRepositoryImpl(service)
    }

    @Test
    fun `should fetch facts successfully`() = runBlocking {
        val responseList = listOf(
            FactResponse("id", listOf(), "url", "description"),
            FactResponse("id", listOf("category"), "url", "description")
        )
        val response = FactsListResponse(responseList)

        val facts = listOf(
            Fact("id", null, "url", "description"),
            Fact("id", "category", "url", "description")
        )

        coEvery { service.fetchFacts(any()) } returns response

        assertEquals(facts, repository.fetchFacts("searchWord"))

        coVerify(exactly = 1) { service.fetchFacts("searchWord") }

        confirmVerified(service)
    }
}
