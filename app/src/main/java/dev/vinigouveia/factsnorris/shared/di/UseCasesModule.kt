package dev.vinigouveia.factsnorris.shared.di

import dev.vinigouveia.factsnorris.shared.usecases.AreCategoriesSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.AreCategoriesSavedUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.DeleteLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.DeleteLastSearchWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.DeleteSearchWordByWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.DeleteSearchWordByWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.FetchCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchCategoriesUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.FetchFactsUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordListUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetLastSearchWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetMappedFactsListUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCase
import dev.vinigouveia.factsnorris.shared.usecases.GetRandomSuggestionsUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.IsLastSearchesListFullUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsLastSearchesListFullUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.IsSearchWordSavedUseCase
import dev.vinigouveia.factsnorris.shared.usecases.IsSearchWordSavedUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.SaveCategoriesUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveCategoriesUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.SaveExistingSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveExistingSearchWordUseCaseImpl
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCase
import dev.vinigouveia.factsnorris.shared.usecases.SaveSearchWordUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

val useCasesModule = module {
    single<FetchFactsUseCase> { FetchFactsUseCaseImpl(get()) }
    single<GetLastSearchWordUseCase> { GetLastSearchWordUseCaseImpl(get()) }
    single<GetMappedFactsListUseCase> { GetMappedFactsListUseCaseImpl(androidContext()) }
    single<DeleteLastSearchWordUseCase> { DeleteLastSearchWordUseCaseImpl(get()) }
    single<FetchCategoriesUseCase> { FetchCategoriesUseCaseImpl(get()) }
    single<GetLastSearchWordListUseCase> { GetLastSearchWordListUseCaseImpl(get()) }
    single<GetRandomSuggestionsUseCase> { GetRandomSuggestionsUseCaseImpl(get()) }
    single<SaveCategoriesUseCase> { SaveCategoriesUseCaseImpl(get()) }
    single<SaveSearchWordUseCase> { SaveSearchWordUseCaseImpl(get()) }
    single<IsLastSearchesListFullUseCase> { IsLastSearchesListFullUseCaseImpl() }
    single<AreCategoriesSavedUseCase> { AreCategoriesSavedUseCaseImpl(get()) }
    single<IsSearchWordSavedUseCase> { IsSearchWordSavedUseCaseImpl(get()) }
    single<DeleteSearchWordByWordUseCase> { DeleteSearchWordByWordUseCaseImpl(get()) }
    single<SaveExistingSearchWordUseCase> { SaveExistingSearchWordUseCaseImpl(get(), get()) }
}
