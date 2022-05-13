package com.example.pokdexproject.data.pokemon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.network.PokemonApiData

@Entity(tableName = "Pokemon")
class PokemonData(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val name: String = "",
    @ColumnInfo val spriteURL: String = "",
    @ColumnInfo val type1: String = "",
    @ColumnInfo val type2: String? = null,
    @ColumnInfo val isBookmarked: Boolean = false,
    @ColumnInfo val isOnTeam: Boolean = false
    //Todo: Expand using detail data from PokeAPI
) {
}

fun PokemonApiData.asDatabaseModel(): PokemonData {
    return PokemonData(
        id,
        name,
        sprites.front_default,
        types[0].type.name,
        if (types.size > 1) {
            (this.types[1].type.name)
        } else {
            null
        }
    )
}