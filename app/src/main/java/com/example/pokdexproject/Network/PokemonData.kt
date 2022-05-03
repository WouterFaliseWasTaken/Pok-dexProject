package com.example.pokdexproject.Network

import com.squareup.moshi.Json

data class PokemonData(
    val id:Int,
    val name:String,
    val sprites:List<PokemonImage>,
    val types: List<Type>
)

data class PokemonImage(
    val front_default:String
)

data class Type(
    val slot:Int,
    val type:List<TypeName>,
)

data class TypeName(
    val name: String
)