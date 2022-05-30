package com.example.pokdexproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdexproject.databinding.TypeViewForListBinding
import com.example.pokdexproject.model.Type

class TypeListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Type, TypeListAdapter.PokemonDataViewHolder>(
        DiffCallBack
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PokemonDataViewHolder {
        return PokemonDataViewHolder(
            TypeViewForListBinding.inflate(
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

    companion object DiffCallBack : DiffUtil.ItemCallback<Type>() {
        override fun areItemsTheSame(
            oldItem: Type,
            newItem: Type
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Type,
            newItem: Type
        ): Boolean {
            return oldItem == newItem
        }


    }

    class OnClickListener(val clickListener: (apiData: Type) -> Unit) {
        fun onClick(apiData: Type) = clickListener(apiData)
    }

    class PokemonDataViewHolder(private var binding: TypeViewForListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(type: Type) {
            binding.typeEnum = type
        }
    }
}