package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository

/**
 * @author Vinicius Gouveia on 30/06/2021
 */
interface SaveCategoriesUseCase {
    suspend fun saveCategories(categories: List<String>)
}

class SaveCategoriesUseCaseImpl(
    private val repository: CategoriesRepository
) : SaveCategoriesUseCase {
    override suspend fun saveCategories(categories: List<String>) =
        repository.saveCategories(categories)
}
