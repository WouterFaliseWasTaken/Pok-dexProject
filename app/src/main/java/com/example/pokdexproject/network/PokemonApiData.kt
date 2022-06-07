package com.example.pokdexproject.network

data class PokemonApiData(
    val id: Int,
    val name: String,
    val sprites: PokemonImage,
    val types: List<TypeData>
)

data class PokemonImage(
    val front_default: String
)

data class TypeData(
    val slot: Int,
    val type: TypeName,
)

data class TypeName(
    val name: String
)



