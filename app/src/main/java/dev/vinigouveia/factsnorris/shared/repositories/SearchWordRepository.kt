package dev.vinigouveia.factsnorris.shared.repositories

import dev.vinigouveia.factsnorris.shared.data.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.room.dao.SearchWordDao

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface SearchWordRepository {
    suspend fun saveSearchWord(searchWord: SearchWordEntity)
    suspend fun getLatestSearchWord(): String
    suspend fun getLastSearchWordList(): List<String>
    suspend fun deleteLastSearchWord()
    suspend fun deleteSearchWordByWord(searchWord: String)
}

class SearchWordRepositoryImpl(
    private val searchWordDao: SearchWordDao
) : SearchWordRepository {
    override suspend fun saveSearchWord(searchWord: SearchWordEntity) =
        searchWordDao.insertSearchWord(searchWord)

    override suspend fun getLatestSearchWord() =
        searchWordDao.getLatestSearchWord().searchWord

    override suspend fun getLastSearchWordList() =
        searchWordDao.getLastSearchWordList().map(SearchWordEntity::searchWord)

    override suspend fun deleteLastSearchWord() =
        searchWordDao.deleteLastSearchWord()

    override suspend fun deleteSearchWordByWord(searchWord: String) =
        searchWordDao.deleteSearchWordByWord(searchWord)
}
