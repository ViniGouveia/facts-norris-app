package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandler
import dev.vinigouveia.factsnorris.shared.errorhandler.ErrorHandlerImpl
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 26/06/2021
 */

private val errorHandlerModule = module {
    single<ErrorHandler> { ErrorHandlerImpl() }
}

val appModules = listOf(
    errorHandlerModule
) + networkModules + databaseModule + repositoriesModule + useCasesModule
