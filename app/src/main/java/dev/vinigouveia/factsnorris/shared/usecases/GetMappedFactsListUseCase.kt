package dev.vinigouveia.factsnorris.shared.usecases

import android.content.Context
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.data.FactDisplay

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
interface GetMappedFactsListUseCase {
    suspend fun getMappedFactsList(factsList: List<Fact>): List<FactDisplay>
}

class GetMappedFactsListUseCaseImpl(
    private val context: Context
) : GetMappedFactsListUseCase {
    override suspend fun getMappedFactsList(factsList: List<Fact>) =
        factsList.map { fact ->
            FactDisplay(
                id = fact.id,
                category = fact.category?.capitalize(Locale.current)
                    ?: context.getString(R.string.fact_uncategorized),
                description = fact.description,
                fontSize = if (fact.description.length > SMALL_FONT_LENGTH_LIMIT) R.dimen.text_medium
                else R.dimen.text_large
            )
        }

    private companion object {
        const val SMALL_FONT_LENGTH_LIMIT = 80
    }
}
