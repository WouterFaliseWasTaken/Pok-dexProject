package com.example.pokdexproject.model

import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.move.MoveData
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData

const val LANGUAGE = "en"

data class DetailedPokemonModel(
    val name: String,
    val id: Int,
    val flavorText: String,
    val images: List<String>,
    val type1: Type,
    val type2: Type,
    val height: Int,//Height in decimeter
    val weight: Int,//Weight in heptogram
    val category: String,
    val genderRatio: Int,//ratio of females in increments of 12.5%
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
    val speed: Int,
    val total: Int
)

data class Move(
    val level: Int,
    val name: String
)

data class EvoChain(
    val grandPredecessorId: Int?,
    val predecessorId: Int?,
    val childrenIds: List<Int>,
    val grandChildrenIds: List<Int>
)

fun getDetailsFromDatabase(
    pokemonData: PokemonData,
    detailsData: DetailsData,
    imageData: List<String>,
    abilityData: List<AbilityData>,
    moveData: List<Pair<Int, MoveData>>,
    doubleEvolvedFromId: Int,
    evolvedIds: List<Int>,
    doubleEvolvedIds: List<Int>
): DetailedPokemonModel {
    val stats = listOf(
        detailsData.hp,
        detailsData.attack,
        detailsData.defense,
        detailsData.spattack,
        detailsData.spdeffense,
        detailsData.speed
    )
    return DetailedPokemonModel(
        pokemonData.name,
        pokemonData.id,
        detailsData.flavorText,
        imageData,
        Type.valueOf(pokemonData.type1),
        if (!pokemonData.type2.isNullOrBlank()) Type.valueOf(pokemonData.type2) else {
            Type.NULLTYPE
        },
        detailsData.height,
        detailsData.weight,
        detailsData.category,
        detailsData.genderRatio,
        abilityData.map { it.name },
        StatsSlate(
            stats[0], stats[1], stats[2], stats[3], stats[4], stats[5],
            stats.average().toInt()
        ),
        moveData.map { Move(it.first, it.second.name) },
        EvoChain(doubleEvolvedFromId,detailsData.evolvesFromId, evolvedIds, doubleEvolvedIds)
    )
}
