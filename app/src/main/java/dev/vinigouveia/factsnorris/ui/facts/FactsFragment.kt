package dev.vinigouveia.factsnorris.ui.facts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.databinding.FactsFragmentBinding
import dev.vinigouveia.factsnorris.shared.classes.FactState
import dev.vinigouveia.factsnorris.shared.classes.fact.Fact
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf

class FactsFragment : Fragment() {

    private lateinit var viewBind: FactsFragmentBinding

    private val binding get() = viewBind

    private val adapter: FactsAdapter by inject()
    private val viewModel: FactsViewModel by viewModel { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBind = FactsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(factsModule)
        initializeElements()
        initializeEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(factsModule)
    }

    private fun initializeEvents() {
        binding.apply {
            val stateObserver = Observer<FactState> {
                adapter.submitList(it.facts)
                factsQuantity.text = it.quantity ?: getString(R.string.facts_quantity_empty)
                loading.isVisible = it.isLoading
                errorText.text = getString(it.errorMessage)
                factsList.isVisible = !it.hasError
                errorText.isVisible = it.hasError
                factsCategory.text = it.searchWord ?: getString(R.string.facts_category_empty)
            }

            with(viewModel) {
                viewLifecycleOwner.also {
                    factState.observe(it, stateObserver)
                }

                getLastSearchWordAndFetchFacts()
            }
        }
    }

    private fun initializeElements() {
        binding.apply {
            factsList.also {
                it.adapter = adapter.apply {
                    onItemClick = { id -> shareFact(viewModel.shareFact(id)) }
                }
                it.layoutManager = LinearLayoutManager(requireContext())
            }
            errorText.setOnClickListener { viewModel.getLastSearchWordAndFetchFacts() }
            searchButton.setOnClickListener { viewModel.navigateToShareFragment() }
        }
    }

    private fun shareFact(fact: Fact) {
        ShareCompat.IntentBuilder(requireContext())
            .setType(SHARE_TYPE)
            .setText(getString(R.string.share_fact_message, fact.description, fact.url))
            .startChooser()
    }

    private companion object {
        const val SHARE_TYPE = "text/plain"
    }
}
