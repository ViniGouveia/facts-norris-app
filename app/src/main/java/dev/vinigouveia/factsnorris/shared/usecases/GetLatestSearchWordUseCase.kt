package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.SearchTermNotFoundException
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
interface GetLatestSearchWordUseCase {
    suspend fun getLatestSearchWord(): String
}

class GetLatestSearchWordUseCaseImpl(
    private val repository: SearchWordRepository
) : GetLatestSearchWordUseCase {
    override suspend fun getLatestSearchWord(): String =
        try {
            repository.getLatestSearchWord()
        } catch (@Suppress("TooGenericExceptionCaught") error: Exception) {
            throw SearchTermNotFoundException
        }
}
