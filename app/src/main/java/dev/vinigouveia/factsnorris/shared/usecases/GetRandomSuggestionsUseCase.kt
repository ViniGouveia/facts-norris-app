package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface GetRandomSuggestionsUseCase {
    suspend fun getRandomSuggestions(): List<String>
}

class GetRandomSuggestionsUseCaseImpl(
    private val repository: CategoriesRepository
) : GetRandomSuggestionsUseCase {
    override suspend fun getRandomSuggestions() = repository.getRandomSuggestions()
}
