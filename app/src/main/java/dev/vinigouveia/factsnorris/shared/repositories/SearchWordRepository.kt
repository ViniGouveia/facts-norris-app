package dev.vinigouveia.factsnorris.shared.repositories

import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.room.dao.SearchWordDao

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface SearchWordRepository {
    suspend fun insertSearchWord(searchWord: SearchWordEntity)
    suspend fun getLastSearchWord(): SearchWordEntity
    suspend fun getLastSearchWordList(): List<SearchWordEntity>
    suspend fun deleteLastSearchWord()
}

class SearchWordRepositoryImpl(
    private val searchWordDao: SearchWordDao
) : SearchWordRepository {
    override suspend fun insertSearchWord(searchWord: SearchWordEntity) =
        searchWordDao.insertSearchWord(searchWord)

    override suspend fun getLastSearchWord() =
        searchWordDao.getLastSearchWord()

    override suspend fun getLastSearchWordList() =
        searchWordDao.getLastSearchWordList()

    override suspend fun deleteLastSearchWord() =
        searchWordDao.deleteLastSearchWord()
}
