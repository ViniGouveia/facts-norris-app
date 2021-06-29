package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
interface GetLastSearchWordUseCase {
    suspend fun getLastSearchWord(): String
}

class GetLastSearchWordUseCaseImpl(
    private val repository: SearchWordRepository
) : GetLastSearchWordUseCase {
    override suspend fun getLastSearchWord(): String =
        try {
            repository.getLastSearchWord()
        } catch (@Suppress("TooGenericExceptionCaught") error: Exception) {
            throw SearchTermNotFoundException
        }

    object SearchTermNotFoundException : Exception()
}
