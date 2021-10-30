package dev.vinigouveia.factsnorris.shared.classes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey @ColumnInfo(name = "category") val category: String
)
