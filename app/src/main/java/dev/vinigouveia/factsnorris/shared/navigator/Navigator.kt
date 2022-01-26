package dev.vinigouveia.factsnorris.shared.navigator

import androidx.annotation.IdRes
import androidx.navigation.NavController

/**
 * @author Vinicius Gouveia on 27/06/2021
 */

interface Navigator {
    fun navigate(@IdRes destinationId: Int)
    fun popBack()
}

class NavigatorImpl(
    private val navController: NavController?
) : Navigator {

    override fun navigate(destinationId: Int) {
        navController?.navigate(destinationId)
    }

    override fun popBack() {
        navController?.popBackStack()
    }
}
