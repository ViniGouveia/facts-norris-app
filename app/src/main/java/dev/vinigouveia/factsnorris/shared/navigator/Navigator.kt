package dev.vinigouveia.factsnorris.shared.navigator

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination

/**
 * @author Vinicius Gouveia on 27/06/2021
 */

interface Navigator {
    fun setNavigator(navController: NavController)
    fun resetNavigator()

    fun navigate(@IdRes destinationId: Int)
    fun popBack()
}

class NavigatorImpl : Navigator {

    private var navController: NavController? = null

    override fun setNavigator(navController: NavController) {
        this.navController = navController
    }

    override fun resetNavigator() {
        this.navController = null
    }

    override fun navigate(destinationId: Int) {
        navController?.navigate(destinationId)
    }

    override fun popBack() {
        navController?.popBackStack()
    }
}
