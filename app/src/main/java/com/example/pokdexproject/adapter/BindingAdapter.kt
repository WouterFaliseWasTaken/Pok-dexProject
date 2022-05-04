package com.example.pokdexproject.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokdexproject.network.PokemonData
import com.example.pokdexproject.R

@BindingAdapter("imageURL")
fun bindImage(imgView: ImageView, imgUrl:String){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
}

@BindingAdapter("listItem")
fun bindRecyclerView(recyclerView:RecyclerView,data: List<PokemonData>){
    val adapter = recyclerView.adapter as PokemonListAdapter
    adapter.submitList(data)
}

class BindingAdapter{

}