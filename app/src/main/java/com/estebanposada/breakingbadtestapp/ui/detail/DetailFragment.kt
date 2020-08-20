package com.estebanposada.breakingbadtestapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.estebanposada.breakingbadtestapp.R
import com.estebanposada.breakingbadtestapp.databinding.FragmentDetailBinding
import com.estebanposada.breakingbadtestapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.getCharacterInfo(id)
        viewModel.detail.observe(viewLifecycleOwner, Observer { ch ->
            val icon = if (ch.favorite) R.drawable.ic_favorite__on else R.drawable.ic_favorite__off
            binding.chFavorite.setImageDrawable(requireContext().getDrawable(icon))
            binding.chFavorite.setOnClickListener { viewModel.onFavoriteClicked(id) }
            Glide.with(requireContext()).load(ch.img).centerInside().into(binding.chImg)
            binding.chNick.text = ch.nickname
            binding.chOccupationText.text = ch.occupation.joinToString()
            binding.chStatusText.text = ch.status
            binding.chPortrayedText.text = ch.portrayed
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}