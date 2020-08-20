package com.estebanposada.breakingbadtestapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.estebanposada.breakingbadtestapp.databinding.FragmentMainBinding
import com.estebanposada.breakingbadtestapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val adapter = CharacterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvCharacters.addItemDecoration(decoration)
        binding.rvCharacters.adapter = adapter

        adapter.onSelectedItem = { id, title ->
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(id, title))
        }
        binding.search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(v.text.toString())
            }
            true
        }
        observeCharacters()
        val query = savedInstanceState?.getString(SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.search(query)
        binding.retryButton.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            it.visibility = View.GONE
            viewModel.search(query)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_QUERY, binding.search.text.toString())
    }

    private fun observeCharacters() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            binding.progress.visibility = View.GONE
            adapter.submitList(it)
            binding.retryButton.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SEARCH_QUERY: String = "search_query"
        private const val DEFAULT_QUERY = ""
    }
}