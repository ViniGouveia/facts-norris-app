package dev.vinigouveia.factsnorris.shared.usecases

import android.content.Context
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import dev.vinigouveia.factsnorris.shared.classes.fact.FactDisplay
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository

interface FactsUseCase {
    suspend fun fetchFacts(searchWord: String): List<Fact>
    suspend fun getLatestSearchWord(): String?
    suspend fun getMappedFactsList(factsList: List<Fact>): List<FactDisplay>
}

class FactsUseCaseImpl(
    private val factsRepository: FactsRepository,
    private val searchWordRepository: SearchWordRepository
) : FactsUseCase {
    override suspend fun fetchFacts(searchWord: String) = factsRepository.fetchFacts(searchWord)

    override suspend fun getLatestSearchWord() = searchWordRepository.getLatestSearchWord()

    override suspend fun getMappedFactsList(factsList: List<Fact>) =
        factsList.map { fact ->
            FactDisplay(
                id = fact.id,
                category = fact.category?.capitalize(Locale.current)
                    ?: UNCATEGORIZED,
                description = fact.description,
                fontSize = if (fact.description.length > SMALL_FONT_LENGTH_LIMIT) R.dimen.text_medium
                else R.dimen.text_large
            )
        }

    private companion object {
        const val SMALL_FONT_LENGTH_LIMIT = 80
        const val UNCATEGORIZED = "Uncategorized"
    }
}
