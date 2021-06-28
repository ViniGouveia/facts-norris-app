package dev.vinigouveia.factsnorris.shared.repositories

import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.service.FactsService

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface FactsRepository {
    suspend fun fetchFacts(searchWord: String): List<Fact>
}

class FactsRepositoryImpl(private val service: FactsService) : FactsRepository {
    override suspend fun fetchFacts(searchWord: String): List<Fact> =
        service.fetchFacts(searchWord).factsList.map { response ->
            Fact(
                id = response.id,
                category = response.categories.firstOrNull(),
                url = response.url,
                description = response.description
            )
        }
}
