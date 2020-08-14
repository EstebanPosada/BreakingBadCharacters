package com.estebanposada.breakingbadtestapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.estebanposada.breakingbadtestapp.R
import com.estebanposada.breakingbadtestapp.databinding.FragmentMainBinding
import com.estebanposada.breakingbadtestapp.ui.MainVIewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainVIewModel by viewModels()

    private val adapter = ItemAdapter(/*viewModel::onCharacterClicked*/)

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

        viewModel.getCharacters()
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}