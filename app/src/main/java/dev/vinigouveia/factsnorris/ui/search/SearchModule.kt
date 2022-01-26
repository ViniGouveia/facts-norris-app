package dev.vinigouveia.factsnorris.ui.search

import androidx.navigation.fragment.findNavController
import dev.vinigouveia.factsnorris.shared.navigator.NavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

val searchModule = module {
    factory { LastSearchesAdapter() }
    viewModel { (fragment: SearchFragment) ->
        SearchViewModel(
            NavigatorImpl(fragment.findNavController()),
            get(),
            get()
        )
    }
}
