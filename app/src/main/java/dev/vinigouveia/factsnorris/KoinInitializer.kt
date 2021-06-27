package dev.vinigouveia.factsnorris

import android.app.Application
import dev.vinigouveia.factsnorris.shared.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @author Vinicius Gouveia on 26/06/2021
 */
class KoinInitializer : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinInitializer)
            modules(appModules)
        }
    }
}
