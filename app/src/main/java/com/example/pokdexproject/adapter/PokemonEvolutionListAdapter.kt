package com.example.pokdexproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.databinding.ListEvolutionItemBinding
import com.example.pokdexproject.model.PokemonEvolutionModel


class PokemonEvolutionListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PokemonEvolutionModel, PokemonEvolutionListAdapter.PokemonDataViewHolder>(
        DiffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PokemonDataViewHolder {
        return PokemonDataViewHolder(
            ListEvolutionItemBinding.inflate(
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

    companion object DiffCallBack : DiffUtil.ItemCallback<PokemonEvolutionModel>() {
        override fun areItemsTheSame(
            oldItem: PokemonEvolutionModel,
            newItem: PokemonEvolutionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PokemonEvolutionModel,
            newItem: PokemonEvolutionModel
        ): Boolean {
            return oldItem == newItem
        }


    }

    class OnClickListener(val clickListener: (apiData: PokemonEvolutionModel) -> Unit) {
        fun onClick(apiData: PokemonEvolutionModel) = clickListener(apiData)
    }

    class PokemonDataViewHolder(private var binding: ListEvolutionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonModel: PokemonEvolutionModel) {
            binding.pokemon = pokemonModel
        }
    }


}