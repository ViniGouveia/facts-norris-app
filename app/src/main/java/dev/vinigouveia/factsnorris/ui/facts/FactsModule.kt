package dev.vinigouveia.factsnorris.ui.facts

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 26/06/2021
 */

val factsModule = module {
    factory { FactsAdapter() }
    viewModel {
        FactsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
