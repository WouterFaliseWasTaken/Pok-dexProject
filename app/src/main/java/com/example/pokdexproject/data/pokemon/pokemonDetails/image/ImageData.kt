package com.example.pokdexproject.data.pokemon.pokemonDetails.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.network.PokemonDetailApiData
import com.example.pokdexproject.network.Sprites

@Entity(tableName = "Image")
data class ImageData(
    @PrimaryKey val url: String = "",
    @ColumnInfo val ownerId: Int = 0
)

fun PokemonDetailApiData.asImageData(): List<ImageData> {
    val list = mutableListOf<ImageData>()
    if(!sprites.other?.officialArtwork?.frontDefault.isNullOrBlank())list.add(ImageData(sprites.other!!.officialArtwork.frontDefault,id))
    if(!sprites.frontDefault.isNullOrBlank())list.add(ImageData(sprites.frontDefault,id))
    if(!sprites.frontFemale.isNullOrBlank())list.add(ImageData(sprites.frontFemale,id))
    if(!sprites.frontShiny.isNullOrBlank())list.add(ImageData(sprites.frontShiny,id))
    if(!sprites.frontShinyFemale.isNullOrBlank())list.add(ImageData(sprites.frontShinyFemale,id))
    if(!sprites.backDefault.isNullOrBlank())list.add(ImageData(sprites.backDefault,id))
    if(!sprites.backFemale.isNullOrBlank())list.add(ImageData(sprites.backFemale,id))
    if(!sprites.backShiny.isNullOrBlank())list.add(ImageData(sprites.backShiny,id))
    if(!sprites.backShinyFemale.isNullOrBlank())list.add(ImageData(sprites.backShinyFemale,id))
    return list
}