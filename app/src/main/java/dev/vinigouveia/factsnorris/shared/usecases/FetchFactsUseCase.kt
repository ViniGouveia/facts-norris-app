package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyFactsListReturnedException
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
interface FetchFactsUseCase {
    suspend fun fetchFacts(searchWord: String): List<Fact>
}

class FetchFactsUseCaseImpl(
    private val repository: FactsRepository
) : FetchFactsUseCase {
    override suspend fun fetchFacts(searchWord: String): List<Fact> {
        val response = repository.fetchFacts(searchWord)

        if (response.isEmpty()) throw EmptyFactsListReturnedException
        else return response
    }
}
