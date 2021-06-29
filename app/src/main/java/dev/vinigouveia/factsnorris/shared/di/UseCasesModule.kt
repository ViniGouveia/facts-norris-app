package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

val useCasesModule = module {
    single<FetchFactsUseCase> { FetchFactsUseCaseImpl(get()) }
    single<GetLastSearchWordUseCase> { GetLastSearchWordUseCaseImpl(get()) }
    single<GetMappedFactsListUseCase> { GetMappedFactsListUseCaseImpl(androidContext()) }
}
