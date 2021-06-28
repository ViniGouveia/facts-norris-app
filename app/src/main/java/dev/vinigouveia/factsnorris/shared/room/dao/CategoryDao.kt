package dev.vinigouveia.factsnorris.shared.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.vinigouveia.factsnorris.shared.data.entities.CategoryEntity

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories_table ORDER BY RANDOM() LIMIT 8")
    suspend fun getRandomSuggestions(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoryEntity>)
}
