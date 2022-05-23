package com.example.pokdexproject

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.model.PokemonModel


@BindingAdapter("imageURL")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
        }
    }
}


@BindingAdapter("listItem")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<PokemonModel>?) {
    val adapter = recyclerView.adapter as PokemonListAdapter
    adapter.submitList(data)
}