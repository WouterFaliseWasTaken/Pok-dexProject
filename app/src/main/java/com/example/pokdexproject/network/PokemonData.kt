package com.example.pokdexproject.network

import android.util.Log
import com.example.pokdexproject.R
import com.example.pokdexproject.model.TAG
import kotlin.coroutines.coroutineContext

data class PokemonData(
    val id: Int,
    val name: String,
    val sprites: PokemonImage,
    val types: List<TypeData>
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

    /**
     * Returns Enum object Type as indicated by the string. If the string is null, returns a nulltype enum which will not be rendered.
     */
    fun parseString(s:String?):Type{
        if(s.isNullOrBlank()) return Type.NULLTYPE
        Log.d(TAG,"${s.uppercase()}")
        return Type.valueOf((s.uppercase()))
    }

}

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

enum class Type {
    NORMAL, FIRE, FIGHTING, WATER, FLYING, GRASS, POISON, ELECTRIC, GROUND, PSYCHIC, ROCK, ICE, BUG, DRAGON, GHOST, DARK, STEEL, FAIRY,NULLTYPE;

    fun getTypeColor(): Int {
        //9:10 there's a better way to write this. Can't find it though.
        return when (this) {
            NORMAL -> R.color.normal_color
            FIRE -> R.color.fire_color
            FIGHTING -> R.color.fighting_color
            WATER -> R.color.water_color
            FLYING -> R.color.flying_color
            GRASS -> R.color.grass_color
            POISON -> R.color.poison_color
            ELECTRIC -> R.color.electric_color
            GROUND -> R.color.ground_color
            PSYCHIC -> R.color.psychic_color
            ROCK -> R.color.rock_color
            ICE -> R.color.ice_color
            BUG -> R.color.bug_color
            DRAGON -> R.color.dragon_color
            GHOST -> R.color.ghost_color
            DARK -> R.color.dark_color
            STEEL -> R.color.steel_color
            FAIRY -> R.color.fairy_color
            NULLTYPE -> R.color.nulltype_color
        }
    }

    /**
     * Returns Enum as a capitalised, but not all caps. Incase the pokemon only has 1 type, returns an empty string.
     */
    fun unCapsLock():String{
        if(this == NULLTYPE) return ""
        return this.name[0].uppercase()+this.name.substring(1).lowercase()
    }
}


