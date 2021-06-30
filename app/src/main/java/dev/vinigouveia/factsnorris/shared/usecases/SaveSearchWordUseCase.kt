package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface SaveSearchWordUseCase {
    suspend fun saveSearchWord(searchWord: String)
}

class SaveSearchWordUseCaseImpl(
    private val repository: SearchWordRepository
) : SaveSearchWordUseCase {
    override suspend fun saveSearchWord(searchWord: String) =
        repository.saveSearchWord(SearchWordEntity(searchWord = searchWord))
}
