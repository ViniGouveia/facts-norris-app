package dev.vinigouveia.factsnorris

import android.content.Context
import androidx.startup.Initializer
import dev.vinigouveia.factsnorris.shared.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * @author Vinicius Gouveia on 26/06/2021
 */
@Suppress("Unused")
class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication =
        startKoin {
            androidContext(context)
            modules(appModules)
        }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
