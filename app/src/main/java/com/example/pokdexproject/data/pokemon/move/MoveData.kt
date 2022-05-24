package com.example.pokdexproject.data.pokemon.move

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.network.PokemonDetailApiData

@Entity(tableName = "Move")
data class MoveData(
    @PrimaryKey
    val id:Int = 0,
    @ColumnInfo
    val name: String = "",
)

fun PokemonDetailApiData.asMoveData():List<MoveData>{
    return moves.map{MoveData(it.move.url.filter{it.isDigit()}.drop(1).toInt(),it.move.name)}
}

@Entity(primaryKeys = ["moveId", "ownerId"], tableName = "hasMove")
data class MoveDataCrossRef(
    @ColumnInfo val moveId: Int = 0,
    @ColumnInfo val ownerId: Int = 0,
    @ColumnInfo val level:Int = 0
)


fun PokemonDetailApiData.asMoveRelations():List<MoveDataCrossRef>{
    return moves.map{ MoveDataCrossRef(it.move.url.filter{it.isDigit()}.drop(1).toInt(),id) }
}