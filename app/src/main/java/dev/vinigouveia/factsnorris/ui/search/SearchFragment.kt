package dev.vinigouveia.factsnorris.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.vinigouveia.factsnorris.R
import dev.vinigouveia.factsnorris.databinding.SearchFragmentBinding
import dev.vinigouveia.factsnorris.databinding.SuggestionItemBinding
import dev.vinigouveia.factsnorris.shared.classes.SearchState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf

/**
 * @author Vinicius Gouveia on 29/06/2021
 */

class SearchFragment : Fragment(R.layout.search_fragment) {

    private lateinit var viewBind: SearchFragmentBinding

    private val binding get() = viewBind

    private val adapter: LastSearchesAdapter by inject()
    private val viewModel: SearchViewModel by viewModel { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBind = SearchFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(searchModule)
        initializeElements()
        initializeEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(searchModule)
    }

    private fun initializeEvents() {
        binding.apply {
            val searchStateObserver = Observer<SearchState> {
                loading.isVisible = it.isLoading
                adapter.submitList(it.lastSearchesList)
                fillCategorySuggestions(it.suggestionsList)
                suggestionsError.text = getString(it.errorMessage)
                searchSuggestions.isVisible = !it.hasError
                suggestionsError.isVisible = it.hasError
            }

            with(viewModel) {
                viewLifecycleOwner.also {
                    searchState.observe(it, searchStateObserver)
                }
            }
        }
    }

    private fun initializeElements() {
        setupSearchInput()

        binding.apply {
            searchInputLayout.setEndIconOnClickListener {
                viewModel.saveSearchWordAndReturn(searchInput.text.toString())
                hideKeyboard()
            }

            suggestionsError.setOnClickListener {
                viewModel.getSuggestionsAndLastSearches()
            }

            lastSearchesList.also {
                it.adapter = adapter.apply {
                    onItemClick = { search -> viewModel.saveSearchWordAndReturn(search) }
                }
                it.layoutManager = LinearLayoutManager(requireContext())
            }

            backButton.setOnClickListener { viewModel.onBackPressed() }
        }

        viewModel.getSuggestionsAndLastSearches()
    }

    private fun fillCategorySuggestions(categorySuggestions: List<String>) {
        categorySuggestions.forEach { suggestion ->
            val suggestionBind = SuggestionItemBinding.inflate(layoutInflater)
            suggestionBind.root.apply {
                text = suggestion
                setOnClickListener { viewModel.saveSearchWordAndReturn(suggestion) }
            }
            binding.searchSuggestions.addView(suggestionBind.root)
        }
    }

    private fun setupSearchInput() {
        binding.searchInput.apply {
            setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.saveSearchWordAndReturn(view.text.toString())
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun hideKeyboard() {
        (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .also { inputMethodManager ->
                inputMethodManager.hideSoftInputFromWindow(requireView().rootView.windowToken, 0)
            }
    }
}
