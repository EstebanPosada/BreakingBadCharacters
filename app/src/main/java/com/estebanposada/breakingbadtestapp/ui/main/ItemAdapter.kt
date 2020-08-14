package com.estebanposada.breakingbadtestapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.estebanposada.breakingbadtestapp.R
import com.estebanposada.breakingbadtestapp.data.database.Character
import com.estebanposada.breakingbadtestapp.databinding.CharacterItemBinding
import kotlinx.android.synthetic.main.character_item.view.*

class ItemAdapter(/*private val selectedListener: (Int) -> Unit*/) :
    ListAdapter<Character, ItemAdapter.ViewHolder>(COMPARATOR) {

//    private val selectedListener: (Int) -> Unit
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            val icon = if (item.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            itemView.favorite.setImageDrawable(context.getDrawable(icon))
            itemView.name.text = item.name
            itemView.nickname.text = item.nickname
            Glide.with(itemView).load(item.img).centerCrop().into(itemView.preview)
            //itemView.setOnClickListener { selectedListener(item.id) }

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

    inner class ViewHolder(binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
