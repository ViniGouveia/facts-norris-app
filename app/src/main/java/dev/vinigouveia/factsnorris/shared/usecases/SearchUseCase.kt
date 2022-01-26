package dev.vinigouveia.factsnorris.shared.usecases

import dev.vinigouveia.factsnorris.shared.classes.entities.SearchWordEntity
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

interface SearchUseCase {
    suspend fun fetchCategories(): List<String>
    suspend fun saveCategories(categories: List<String>)
    suspend fun getLastSearchWordList(): List<String>
    suspend fun getRandomSuggestions(): List<String>
    suspend fun saveSearchWord(searchWord: String)
    suspend fun deleteLastSearchWord()
    suspend fun areSavedCategories(): Boolean
    suspend fun isSearchWordSaved(searchWord: String): Boolean
    suspend fun saveExistingSearchWord(searchWord: String)
    fun isLastSearchesListFull(size: Int): Boolean
}

class SearchUseCaseImpl(
    private val searchWordRepository: SearchWordRepository,
    private val categoriesRepository: CategoriesRepository
) : SearchUseCase {
    override suspend fun fetchCategories() = categoriesRepository.fetchCategories()

    override suspend fun saveCategories(categories: List<String>) =
        categoriesRepository.saveCategories(categories)

    override suspend fun getLastSearchWordList() =
        searchWordRepository.getLastSearchWordList()

    override suspend fun getRandomSuggestions() = categoriesRepository.getRandomSuggestions()

    override suspend fun saveSearchWord(searchWord: String) =
        searchWordRepository.saveSearchWord(SearchWordEntity(searchWord = searchWord))

    override suspend fun deleteLastSearchWord() =
        searchWordRepository.deleteLastSearchWord()

    override fun isLastSearchesListFull(size: Int) = size == LAST_SEARCHES_MAX_SIZE

    override suspend fun areSavedCategories() =
        categoriesRepository.getRandomSuggestions().isNotEmpty()

    override suspend fun isSearchWordSaved(searchWord: String) =
        searchWordRepository.getLastSearchWordList().contains(searchWord)

    override suspend fun saveExistingSearchWord(searchWord: String) {
        searchWordRepository.deleteSearchWordByWord(searchWord)
        searchWordRepository.saveSearchWord(SearchWordEntity(searchWord = searchWord))
    }

    private companion object {
        const val LAST_SEARCHES_MAX_SIZE = 8
    }
}
