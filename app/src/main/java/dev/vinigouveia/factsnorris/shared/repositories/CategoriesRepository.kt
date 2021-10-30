package dev.vinigouveia.factsnorris.shared.repositories

import dev.vinigouveia.factsnorris.shared.classes.entities.CategoryEntity
import dev.vinigouveia.factsnorris.shared.room.dao.CategoryDao
import dev.vinigouveia.factsnorris.shared.service.CategoriesService

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface CategoriesRepository {
    suspend fun getRandomSuggestions(): List<String>
    suspend fun saveCategories(categories: List<String>)
    suspend fun fetchCategories(): List<String>
}

class CategoriesRepositoryImpl(
    private val categoriesService: CategoriesService,
    private val categoryDao: CategoryDao
) : CategoriesRepository {
    override suspend fun getRandomSuggestions(): List<String> =
        categoryDao.getRandomSuggestions().map(CategoryEntity::category)

    override suspend fun saveCategories(categories: List<String>) =
        categoryDao.insertAllCategories(categories.map(::CategoryEntity))

    override suspend fun fetchCategories() =
        categoriesService.fetchCategories()
}
