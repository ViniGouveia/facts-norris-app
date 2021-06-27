package dev.vinigouveia.factsnorris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dev.vinigouveia.factsnorris.databinding.MainActivityBinding
import dev.vinigouveia.factsnorris.shared.navigator.Navigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val navController = supportFragmentManager
            .findFragmentById(R.id.navigation_host_fragment)!!
            .findNavController()

        navigator.setNavigator(navController)
    }

    override fun onPause() {
        super.onPause()
        navigator.resetNavigator()
    }
}
