package dev.vinigouveia.factsnorris.shared.threadprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @author Vinicius Gouveia on 28/06/2021
 */

interface ThreadProvider {
    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
}

class ThreadProviderImpl : ThreadProvider {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val ui: CoroutineDispatcher = Dispatchers.Main
}
