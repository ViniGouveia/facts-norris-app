package dev.vinigouveia.factsnorris.navigator

import androidx.navigation.NavController
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import dev.vinigouveia.factsnorris.shared.navigator.NavigatorImpl
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * @author Vinicius Gouveia on 01/07/2021
 */
class NavigatorTest {

    private lateinit var navigator: Navigator

    private val navController = mockk<NavController>(relaxed = true)

    @BeforeEach
    fun initialize() {
        navigator = NavigatorImpl()
    }

    @Test
    fun `should set navigator and navigate successfully`() {
        val destinationId = R.id.navigate_to_search_from_facts

        every { navController.navigate(any<Int>()) } just Runs

        navigator.setNavigator(navController)
        navigator.navigate(destinationId)

        verify(exactly = 1) {
            navController.navigate(destinationId)
        }

        confirmVerified(navController)
    }

    @Test
    fun `should reset navigator and do nothing when try to navigate`() {
        val destinationId = R.id.navigate_to_search_from_facts

        navigator.setNavigator(navController)
        navigator.resetNavigator()
        navigator.navigate(destinationId)

        confirmVerified(navController)
    }

    @Test
    fun `should set navigator and pop back successfully`() {
        every { navController.popBackStack() } returns true

        navigator.setNavigator(navController)
        navigator.popBack()

        verify(exactly = 1) { navController.popBackStack() }

        confirmVerified(navController)
    }

    @Test
    fun `should reset navigator and do nothing when try to pop`() {
        navigator.setNavigator(navController)
        navigator.resetNavigator()
        navigator.popBack()

        confirmVerified(navController)
    }
}
