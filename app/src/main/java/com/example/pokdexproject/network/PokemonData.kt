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

enum class Type(val color: Int) {
    NORMAL(0xFFA8A878.toInt()), FIRE(0xFF9C531F.toInt()),
    FIGHTING(0xFF7D1F1A.toInt()), WATER(0xFF445E9C.toInt()),
    FLYING(0xFF6D5E9C.toInt()), GRASS(0xFF4E8234.toInt()),
    POISON(0xFF682A68.toInt()), ELECTRIC(0xFFA1871F.toInt()),
    GROUND(0xFF927D44.toInt()), PSYCHIC(0xFFA13959.toInt()), ROCK(0xFF786824.toInt()),
    ICE(0xFF638D8D.toInt()), BUG(0xFF6D7815.toInt()), DRAGON(0xFF4924A1.toInt()),
    GHOST(0xFF493963.toInt()), DARK(0xFF49392F.toInt()), STEEL(0xFF877887.toInt())
    ,FAIRY(0xFF9B6470.toInt()),NULLTYPE(0xFFFFFFFF.toInt());
    /**
     * Returns Enum as a capitalised, but not all caps. Incase the pokemon only has 1 type, returns an empty string.
     */
    fun unCapsLock():String{
        if(this == NULLTYPE) return ""
        return this.name[0].uppercase()+this.name.substring(1).lowercase()
    }
}


