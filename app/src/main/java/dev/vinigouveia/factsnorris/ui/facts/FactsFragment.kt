package dev.vinigouveia.factsnorris.ui.facts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.vinigouveia.factsnorris.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactsFragment : Fragment() {

    private val factsViewModel: FactsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.facts_fragment, container, false)
    }

    companion object {
        fun newInstance() = FactsFragment()
    }
}
