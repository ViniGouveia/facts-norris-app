package dev.vinigouveia.factsnorris.ui.facts

import androidx.navigation.fragment.findNavController
import dev.vinigouveia.factsnorris.shared.navigator.NavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 26/06/2021
 */

val factsModule = module {
    factory { FactsAdapter() }
    viewModel { (fragment: FactsFragment) ->
        FactsViewModel(
            NavigatorImpl(fragment.findNavController()),
            get(),
            get()
        )
    }
}
