package com.example.pokdexproject.model


data class Pokémon(
    val index: Int,
    val name: String,
    //add image reference here
    val type1: PokémonType,
    val type2: PokémonType?,
    val abilities: List<Ability>,
    val moveset: List<Move>,
    val health: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)

//Consider combining the stats into a map of Key/value pairs.

data class PokémonType(
    val type: TypeEnum
    //add image reference here
) {
    fun receivesBonusDamge(): List<PokémonType> {
        when (type) {
            else -> return listOf<TypeEnum>().map { PokémonType(it) }
        }
    }

    fun receivesReducedDamge(): List<PokémonType> {
        when (type) {
            else -> return listOf<TypeEnum>().map { PokémonType(it) }
        }
    }

    fun isImmuneTo(): List<PokémonType> {
        when (type) {
            else -> return listOf<TypeEnum>().map { PokémonType(it) }
        }
    }
}


enum class TypeEnum { Normal, Fire, Fighting, Water, Flying, Grass, Poison, Electric, Ground, Psychic, Rock, Ice, Bug, Dragon, Ghost, Dark, Steel, Fairy }

data class Ability(val name: String)
//todo

data class Move(val name: String)
//todo
