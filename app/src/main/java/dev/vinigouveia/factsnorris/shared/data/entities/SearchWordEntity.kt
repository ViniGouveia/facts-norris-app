package dev.vinigouveia.factsnorris.shared.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
@Entity(tableName = "last_searches_table")
data class SearchWordEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "searchWord") val searchWord: String
)
