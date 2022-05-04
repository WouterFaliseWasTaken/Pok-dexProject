package com.example.pokdexproject.Adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.Network.PokemonData
import com.example.pokdexproject.databinding.ListItemBinding

/**
class PokemonListAdapter : ListAdapter<PokemonData, PokemonListAdapter.PokemonDataViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup,position:Int){

    }
    override fun onBindViewHolder(holder:ListAdapter.PokemonDataViewHolder, position:Int){

    }




    class PokemonDataViewHolder(private var binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(PokemonData: PokemonData){
            binding.pokemon = PokemonData
            binding.executePendingBindings()
        }
    }


}
*/