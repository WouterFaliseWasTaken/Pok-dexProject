package com.example.pokdexproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.databinding.ListItemBinding
import com.example.pokdexproject.model.PokemonModel


class PokemonListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PokemonModel, PokemonListAdapter.PokemonDataViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PokemonDataViewHolder {
        return PokemonDataViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonDataViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(pokemon)
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<PokemonModel>() {
        override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem == newItem
        }


    }

    class OnClickListener(val clickListener: (apiData: PokemonModel) -> Unit) {
        fun onClick(apiData: PokemonModel) = clickListener(apiData)
    }

    class PokemonDataViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonModel: PokemonModel) {
            binding.pokemon = pokemonModel
        }
    }


}