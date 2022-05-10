package com.example.pokdexproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.network.PokemonData
import com.example.pokdexproject.databinding.ListItemBinding


class PokemonListAdapter : ListAdapter<PokemonData, PokemonListAdapter.PokemonDataViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup,position:Int):PokemonListAdapter.PokemonDataViewHolder
    {
        return PokemonDataViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:PokemonListAdapter.PokemonDataViewHolder, position:Int){
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<PokemonData>(){
        override fun areItemsTheSame(oldItem: PokemonData, newItem: PokemonData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonData, newItem: PokemonData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class PokemonDataViewHolder(private var binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(pokemonData: PokemonData){
            binding.pokemon = pokemonData
        }
    }


}