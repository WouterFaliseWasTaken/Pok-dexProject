package com.example.pokdexproject.data.pokemon.ability

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.network.PokemonDetailApiData

@Entity(tableName = "Ability")
data class AbilityData(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val name: String = "",
    @ColumnInfo val isHidden: Boolean = false
)

fun PokemonDetailApiData.asAbilityData():List<AbilityData>{
    return abilities.map{AbilityData(it.ability.url.filter{it.isDigit()}.toInt(),it.ability.name)}
}

@Entity(tableName = "HasAbility", primaryKeys = ["abilityId","ownerId"])
data class AbilityDataCrossRef(
    @ColumnInfo val abilityId:Int = 0,
    @ColumnInfo val ownerId: Int=0
)

fun PokemonDetailApiData.asAbilityRelations():List<AbilityDataCrossRef>{
    return abilities.map{ AbilityDataCrossRef(it.ability.url.filter{it.isDigit()}.toInt(),id) }
}