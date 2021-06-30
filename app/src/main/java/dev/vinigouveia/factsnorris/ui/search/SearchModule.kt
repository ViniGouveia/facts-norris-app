package dev.vinigouveia.factsnorris.ui.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

val searchModule = module {
    factory { LastSearchesAdapter() }
    viewModel {
        SearchViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
