package dev.vinigouveia.factsnorris.shared.di

import androidx.room.Room
import dev.vinigouveia.factsnorris.shared.room.database.ApplicationDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

private val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ApplicationDatabase::class.java,
            ApplicationDatabase::class.simpleName!!
        ).build()
    }
}

private val daoModule = module {
    single { get<ApplicationDatabase>().categoryDao() }
    single { get<ApplicationDatabase>().searchWordDao() }
}

val databaseModule = listOf(roomDatabaseModule, daoModule)
