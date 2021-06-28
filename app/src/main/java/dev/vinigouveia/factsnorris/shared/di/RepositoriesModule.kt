package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepository
import dev.vinigouveia.factsnorris.shared.repositories.CategoriesRepositoryImpl
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepository
import dev.vinigouveia.factsnorris.shared.repositories.FactsRepositoryImpl
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepository
import dev.vinigouveia.factsnorris.shared.repositories.SearchWordRepositoryImpl
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

val repositoriesModule = module {
    single<FactsRepository> { FactsRepositoryImpl(get()) }
    single<CategoriesRepository> { CategoriesRepositoryImpl(get(), get()) }
    single<SearchWordRepository> { SearchWordRepositoryImpl(get()) }
}
