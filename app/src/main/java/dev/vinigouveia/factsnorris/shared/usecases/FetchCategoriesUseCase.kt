package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.errorhandler.exceptions.EmptyCategoriesListReturnedException
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface FetchCategoriesUseCase {
    suspend fun fetchCategories(): List<String>
}

class FetchCategoriesUseCaseImpl(
    private val repository: CategoriesRepository
) : FetchCategoriesUseCase {
    override suspend fun fetchCategories(): List<String> {
        val response = repository.fetchCategories()

        if (response.isEmpty()) throw EmptyCategoriesListReturnedException
        else return response
    }
}
