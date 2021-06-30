package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface DeleteSearchWordByWordUseCase {
    suspend fun deleteSearchWordByWord(searchWord: String)
}

class DeleteSearchWordByWordUseCaseImpl(
    private val repository: SearchWordRepository
) : DeleteSearchWordByWordUseCase {
    override suspend fun deleteSearchWordByWord(searchWord: String) =
        repository.deleteSearchWordByWord(searchWord)
}
