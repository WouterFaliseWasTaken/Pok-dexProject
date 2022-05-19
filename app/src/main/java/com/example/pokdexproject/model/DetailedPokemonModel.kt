package com.example.pokdexproject.model

import com.example.pokdexproject.network.PokemonDetailApiData
import com.example.pokdexproject.network.PokemonSpeciesApiData

data class DetailedPokemonModel(
    val name: String,
    val id: Int,
    val flavorText:String,
    val type1: Type,
    val type2: Type,
    val height: Int,//Height in decimeter
    val weight: Int,//Weight in heptogram
    val genders: Pair<Boolean, Boolean>,//Male, Female
    val abilities: List<String>,
    val stats: StatsSlate,
    val moveSet: List<Move>,
    val evolutions: EvoChain
)

data class StatsSlate(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val spattack: Int,
    val spdeffense: Int,
    val speed: Int
)

data class Move(
    val level: Int,
    val name: String
)


data class EvoChain(
    val predecessorId: Int?,
    val childrenIds: List<Int>,
    val grandChildrenIds: List<Int>
)

fun getDetailsFromApis(pokemonDetailApiData: PokemonDetailApiData,pokemonSpeciesApiData: PokemonSpeciesApiData):DetailedPokemonModel{
    return DetailedPokemonModel(
            pokemonDetailApiData.name,
            pokemonDetailApiData.id,
            pokemonSpeciesApiData.flavorTextEntries[0].flavorText,//todo: Figure out which to use
            Type.valueOf(pokemonDetailApiData.types[0].type.name.uppercase()),
            if (pokemonDetailApiData.types.size > 1) {
                Type.valueOf(pokemonDetailApiData.types[1].type.name.uppercase())
            } else Type.NULLTYPE,
            pokemonDetailApiData.height,
            pokemonDetailApiData.weight,
            Pair(true,true),//todo: Figure out how to determine this programatically. Nidoran species might help
            pokemonDetailApiData.abilities.map { it.ability.name },
            StatsSlate(
                pokemonDetailApiData.stats[0].baseStat,//hp
                pokemonDetailApiData.stats[1].baseStat,//attack
                pokemonDetailApiData.stats[2].baseStat,//defense
                pokemonDetailApiData.stats[3].baseStat,//spattack
                pokemonDetailApiData.stats[4].baseStat,//spdefense
                pokemonDetailApiData.stats[5].baseStat,//speed
                //concern: I can't prove the API will send these in consistent order
                ),
            listOf<Move>(),//todo: Discuss which ones to even use
            EvoChain(
                null,
                listOf(),
                listOf()
                //todo: get from evolution-chain
            )


        )
}


