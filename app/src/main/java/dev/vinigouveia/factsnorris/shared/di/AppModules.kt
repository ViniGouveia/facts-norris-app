package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandlerImpl
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.navigator.NavigatorImpl
import dev.vinigouveia.factsnorris.shared.threadprovider.ThreadProvider
import dev.vinigouveia.factsnorris.shared.threadprovider.ThreadProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 26/06/2021
 */

private val navigatorModule = module {
    single<Navigator> { NavigatorImpl() }
}

private val threadProviderModule = module {
    single<ThreadProvider> { ThreadProviderImpl() }
}

private val errorHandlerModule = module {
    single<ErrorHandler> { ErrorHandlerImpl(androidContext()) }
}

val appModules = listOf(
    navigatorModule,
    threadProviderModule,
    errorHandlerModule
) + networkModules + databaseModule + repositoriesModule + useCasesModule
