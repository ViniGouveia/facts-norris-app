package dev.vinigouveia.factsnorris.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.vinigouveia.factsnorris.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
