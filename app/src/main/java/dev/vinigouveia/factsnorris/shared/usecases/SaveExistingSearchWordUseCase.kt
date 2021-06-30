package dev.vinigouveia.factsnorris.shared.usecases

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface SaveExistingSearchWordUseCase {
    suspend fun saveExistingSearchWord(searchWord: String)
}

class SaveExistingSearchWordUseCaseImpl(
    private val deleteSearchWordByWordUseCase: DeleteSearchWordByWordUseCase,
    private val saveSearchWordUseCase: SaveSearchWordUseCase
) : SaveExistingSearchWordUseCase {
    override suspend fun saveExistingSearchWord(searchWord: String) {
        deleteSearchWordByWordUseCase.deleteSearchWordByWord(searchWord)
        saveSearchWordUseCase.saveSearchWord(searchWord)
    }
}
