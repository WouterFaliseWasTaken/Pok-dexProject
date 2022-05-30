package com.example.pokdexproject.model

import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.network.PokemonApiData

open class PokemonModel(
    val id: Int,
    val name: String,
    val spriteUrl: String,
    val type1: Type,
    val type2: Type?,
    var isBookmarked: Boolean = false,
    var isOnTeam: Boolean = false
) {
    fun getIdString(): String {
        var idString = "Nr. "
        when (id) {
            in (0..9) -> idString += "00"
            in (10..99) -> idString += "0"
            else -> Unit
        }
        return idString + id.toString()
    }

    fun capitaliseName(): String {
        return this.name[0].uppercase() + this.name.substring(1)
    }
}

fun PokemonData.asDomainModel(): PokemonModel {
    return PokemonModel(
        this.id,
        this.name,
        this.spriteURL,
        Type.valueOf(this.type1.uppercase()),
        Type.valueOf(this.type2?.uppercase() ?: "NULLTYPE"),
        this.isBookmarked,
        this.isOnTeam
    )
}

enum class Type(val color: Int) {
    NORMAL(0xFFA8A878.toInt()), FIRE(0xFFF08030.toInt()),
    FIGHTING(0xFFC03028.toInt()), WATER(0xFF6890F0.toInt()),
    FLYING(0xFFA890F0.toInt()), GRASS(0xFF78C850.toInt()),
    POISON(0xFFA040A0.toInt()), ELECTRIC(0xFFF8D030.toInt()),
    GROUND(0xFFE0C068.toInt()), PSYCHIC(0xFFF85888.toInt()),
    ROCK(0xFFB8A038.toInt()), ICE(0xFF98D8D8.toInt()),
    BUG(0xFFA8B820.toInt()), DRAGON(0xFF7038F8.toInt()),
    GHOST(0xFF705898.toInt()), DARK(0xFF705848.toInt()),
    STEEL(0xFFB8B8D0.toInt()), FAIRY(0xFFEE99AC.toInt()),
    NULLTYPE(0x00FFFFFF.toInt());

    /**
     * Returns Enum as capitalised, but not all caps. In case the pokemon only has 1 type, returns an empty string for the second type so things don't break.
     */
    fun unCapsLock(): String {
        if (this == NULLTYPE) return ""
        return this.name[0].uppercase() + this.name.substring(1).lowercase()
    }
}

data class PokemonEvolutionModel(
    val idS: Int,
    val nameS: String,
    val spriteUrlS: String,
    val type1S: Type,
    val type2S: Type?,
    var isBookmarkedS: Boolean = false,
    var isOnTeamS: Boolean = false,
    val isFocused: Boolean
):PokemonModel(idS,nameS,spriteUrlS,type1S,type2S,isBookmarkedS,isOnTeamS)
fun PokemonData.asEvolutionModel(callerId:Int): PokemonEvolutionModel {
    return PokemonEvolutionModel(
        this.id,
        this.name,
        this.spriteURL,
        Type.valueOf(this.type1.uppercase()),
        Type.valueOf(this.type2?.uppercase() ?: "NULLTYPE"),
        this.isBookmarked,
        this.isOnTeam,
        (callerId == this.id)
    )
}