package dev.vinigouveia.factsnorris.shared.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.vinigouveia.factsnorris.shared.classes.entities.SearchWordEntity

/**
 * @author Vinicius Gouveia on 27/06/2021
 */
@Dao
interface SearchWordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchWord(searchWord: SearchWordEntity)

    @Query("SELECT * FROM last_searches_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestSearchWord(): SearchWordEntity?

    @Query("SELECT * FROM last_searches_table ORDER BY id DESC")
    suspend fun getLastSearchWordList(): List<SearchWordEntity>

    @Query("DELETE FROM last_searches_table WHERE searchWord = :searchWord")
    suspend fun deleteSearchWordByWord(searchWord: String)

    @Query(
        """
        DELETE FROM last_searches_table 
        WHERE id 
        IN (
        SELECT id FROM last_searches_table
            ORDER BY id ASC
            LIMIT 1
        )
        """
    )
    suspend fun deleteLastSearchWord()
}
