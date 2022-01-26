package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FactsUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SearchUseCaseImpl
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

val useCasesModule = module {
    single<FactsUseCase> { FactsUseCaseImpl(get(), get()) }
    single<SearchUseCase> { SearchUseCaseImpl(get(), get()) }
}
