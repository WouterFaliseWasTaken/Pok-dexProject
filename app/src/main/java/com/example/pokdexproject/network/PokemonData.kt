package com.example.pokdexproject.network

data class PokemonData(
    val id:Int,
    val name:String,
    val sprites:PokemonImage,
    val types: List<Type>
)

data class PokemonImage(
    val front_default:String
)

data class Type(
    val slot:Int,
    val type:TypeName,
)

data class TypeName(
    val name: String
)