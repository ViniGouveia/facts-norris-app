package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface GetLastSearchWordListUseCase {
    suspend fun getLastSearchWordList(): List<String>
}

class GetLastSearchWordListUseCaseImpl(
    private val repository: SearchWordRepository
) : GetLastSearchWordListUseCase {
    override suspend fun getLastSearchWordList() =
        repository.getLastSearchWordList()
}
