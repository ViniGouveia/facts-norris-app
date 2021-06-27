package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.navigator.NavigatorImpl
import dev.vinigouveia.factsnorris.shared.network.networkModules
import dev.vinigouveia.factsnorris.ui.facts.factsModule
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 26/06/2021
 */

private val viewModelModules = listOf(
    factsModule
)

private val navigatorModule = module {
    single<Navigator> { NavigatorImpl() }
}

val appModules = listOf(viewModelModules, navigatorModule) + networkModules
