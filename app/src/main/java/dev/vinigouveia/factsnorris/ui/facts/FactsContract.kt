package dev.vinigouveia.factsnorris.ui.facts

import dev.vinigouveia.factsnorris.shared.data.Fact

/**
 * @author Vinicius Gouveia on 29/06/2021
 */
interface FactsContract {
    interface ViewModel {
        fun shareFact(id: String): Fact
        fun getLastSearchWordAndFetchFacts()
        fun navigateToShareFragment()
    }
}
