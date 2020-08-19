package com.estebanposada.breakingbadtestapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.estebanposada.breakingbadtestapp.R
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.databinding.CharacterItemBinding

class CharacterAdapter : PagedListAdapter<Character, CharacterAdapter.ViewHolder>(COMPARATOR) {

    var onSelectedItem: ((Int, String) -> Unit)? = null
    private lateinit var context: Context

    inner class ViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(character: Character) {
            val icon =
                if (character.favorite) R.drawable.ic_favorite__on else R.drawable.ic_favorite__off
            binding.favorite.setImageDrawable(context.getDrawable(icon))
            binding.name.text = character.name
            binding.nickname.text = character.nickname
            Glide.with(context).load(character.img).centerInside().into(binding.preview)
            binding.itemContainer.setOnClickListener { onSelectedItem?.invoke(character.char_id, character.name) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.char_id == newItem.char_id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.name == newItem.name && oldItem.nickname == newItem.nickname

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(CharacterItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bindView(it!!) }
    }
}