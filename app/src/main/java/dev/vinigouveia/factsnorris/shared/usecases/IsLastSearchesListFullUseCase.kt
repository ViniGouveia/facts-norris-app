package dev.vinigouveia.factsnorris.shared.usecases

/**
 * @author Vinicius Gouveia on 30/06/2021
 */

interface IsLastSearchesListFullUseCase {
    fun isLastSearchesListFull(size: Int): Boolean
}

class IsLastSearchesListFullUseCaseImpl : IsLastSearchesListFullUseCase {

    override fun isLastSearchesListFull(size: Int) = size == LAST_SEARCHES_MAX_SIZE

    private companion object {
        const val LAST_SEARCHES_MAX_SIZE = 8
    }
}
