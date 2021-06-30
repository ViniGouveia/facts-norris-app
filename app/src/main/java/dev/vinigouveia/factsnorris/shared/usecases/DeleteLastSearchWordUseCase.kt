package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface DeleteLastSearchWordUseCase {
    suspend fun deleteLastSearchWord()
}

class DeleteLastSearchWordUseCaseImpl(
    private val repository: SearchWordRepository
) : DeleteLastSearchWordUseCase {
    override suspend fun deleteLastSearchWord() =
        repository.deleteLastSearchWord()
}
