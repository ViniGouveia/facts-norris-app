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
import dev.vinigouveia.factsnorris.shared.data.Fact
import dev.vinigouveia.factsnorris.shared.data.FactDisplay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FactsFragment : Fragment() {

    private var viewBind: FactsFragmentBinding? = null

    private val binding get() = viewBind!!

    private val adapter: FactsAdapter by inject()
    private val viewModel: FactsViewModel by viewModel()

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
        viewBind = null
    }

    private fun initializeEvents() {
        binding.apply {
            val factsObserver =
                Observer<List<FactDisplay>> { facts -> adapter.submitList(facts) }
            val searchWordObserver = Observer<String> { category -> factsCategory.text = category }
            val quantityObserver = Observer<String> { quantity -> factsQuantity.text = quantity }
            val loadingStateObserver =
                Observer<Boolean> { isLoading -> loading.isVisible = isLoading }
            val errorMessageObserver =
                Observer<String> { errorMessage -> errorText.text = errorMessage }
            val errorStateObserver = Observer<Boolean> { hasError ->
                factsList.isVisible = !hasError
                errorText.isVisible = hasError
            }

            with(viewModel) {
                viewLifecycleOwner.also {
                    factsList.observe(it, factsObserver)
                    searchWord.observe(it, searchWordObserver)
                    quantity.observe(it, quantityObserver)
                    errorMessage.observe(it, errorMessageObserver)
                    loadingState.observe(it, loadingStateObserver)
                    errorState.observe(it, errorStateObserver)
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
