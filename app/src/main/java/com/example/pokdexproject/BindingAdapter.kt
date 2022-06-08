package com.example.pokdexproject

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokdexproject.adapter.PokemonListAdapter
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.views.HexagonView


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

@BindingAdapter(
    value = ["hp","attack","defense","speed","spattack","spdefense"],
    requireAll = false
)
fun HexagonView.setStats(hp:Int, attack:Int, defense:Int, speed:Int, spattack:Int, spdefense:Int){
    this.stats = listOf(hp,attack,defense,speed,spattack,spdefense).map{it.toFloat()}
    invalidate()
}

