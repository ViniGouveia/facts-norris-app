package dev.vinigouveia.factsnorris.shared.usecases

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface AreCategoriesSavedUseCase {
    suspend fun areSavedCategories(): Boolean
}

class AreCategoriesSavedUseCaseImpl(
    private val getRandomSuggestionsUseCase: GetRandomSuggestionsUseCase
) : AreCategoriesSavedUseCase {
    override suspend fun areSavedCategories() =
        getRandomSuggestionsUseCase.getRandomSuggestions().isNotEmpty()
}
