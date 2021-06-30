package dev.vinigouveia.factsnorris.shared.usecases

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface IsSearchWordSavedUseCase {
    suspend fun isSearchWordSaved(searchWord: String): Boolean
}

class IsSearchWordSavedUseCaseImpl(
    private val getLastSearchWordListUseCase: GetLastSearchWordListUseCase
) : IsSearchWordSavedUseCase {
    override suspend fun isSearchWordSaved(searchWord: String) =
        getLastSearchWordListUseCase.getLastSearchWordList().contains(searchWord)
}
