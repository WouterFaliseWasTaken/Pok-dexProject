package com.example.pokdexproject.data.pokemon.type


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Type")
class TypeData (
    @PrimaryKey val id:Int,
    @ColumnInfo val name:String
)

@Entity(tableName = "DealsDamageTo", primaryKeys = ["attackType","defenseType"])
data class TypeDataDamageRef(
    @ColumnInfo val attackType:Int,
    @ColumnInfo val defenseType:Int,
    @ColumnInfo val damageModifier:Double//0, 0.5, 1, or 2.
)