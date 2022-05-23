package com.example.pokdexproject.data.pokemon.pokemonDetails.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.network.PokemonDetailApiData

@Entity(tableName = "Image")
data class ImageData(
    @PrimaryKey val url: String = "",
    @ColumnInfo val ownerId: Int = 0
)

fun PokemonDetailApiData.asImageData(): List<ImageData> {
    return list
}