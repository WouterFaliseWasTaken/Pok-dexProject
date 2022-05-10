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
    fun capitaliseName():String{
        return this.name[0].uppercase()+this.name.substring(1).lowercase()
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
    NORMAL(0xFFA8A878.toInt()), FIRE(0xFFF08030.toInt()),
    FIGHTING(0xFFC03028.toInt()), WATER(0xFF6890F0.toInt()),
    FLYING(0xFFA890F0.toInt()), GRASS(0xFF78C850.toInt()),
    POISON(0xFFA040A0.toInt()), ELECTRIC(0xFFF8D030.toInt()),
    GROUND(0xFFE0C068.toInt()), PSYCHIC(0xFFF85888.toInt()),
    ROCK(0xFFB8A038.toInt()), ICE(0xFF98D8D8.toInt()),
    BUG(0xFFA8B820.toInt()), DRAGON(0xFF7038F8.toInt()),
    GHOST(0xFF705898.toInt()), DARK(0xFF705848.toInt()),
    STEEL(0xFFB8B8D0.toInt()),FAIRY(0xFFEE99AC.toInt())
    ,NULLTYPE(0xFFFFFFFF.toInt());
    /**
     * Returns Enum as capitalised, but not all caps. Incase the pokemon only has 1 type, returns an empty string.
     */
    fun unCapsLock():String{
        if(this == NULLTYPE) return ""
        return this.name[0].uppercase()+this.name.substring(1).lowercase()
    }
}


