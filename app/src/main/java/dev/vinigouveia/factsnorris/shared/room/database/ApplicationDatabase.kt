package dev.vinigouveia.factsnorris.shared.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.vinigouveia.factsnorris.shared.data.entities.CategoryEntity
import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.room.dao.CategoryDao
import dev.vinigouveia.factsnorris.shared.room.dao.SearchWordDao

/**
 * @author Vinicius Gouveia on 28/06/2021
 */
@Database(
    entities = [
        CategoryEntity::class,
        SearchWordEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun searchWordDao(): SearchWordDao
}
