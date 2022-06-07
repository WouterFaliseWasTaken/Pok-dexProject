package com.example.pokdexproject.data.pokemon.type


import TypeApiData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Type")
class TypeData(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String
)

fun TypeApiData.toTypeModel(): TypeData {
    return TypeData(
        this.id,
        this.name
    )
}

@Entity(tableName = "DealsDamageTo", primaryKeys = ["attackType", "defenseType"])
data class TypeDataDamageRef(
    @ColumnInfo val attackType: Int,
    @ColumnInfo val defenseType: Int,
    @ColumnInfo val damageModifier: Int//0, 1,2, or 4(double the actual values so I can keep these as integer values. )
)

fun TypeApiData.toDatabaseModel(): List<TypeDataDamageRef> {
    val list: MutableList<TypeDataDamageRef> = mutableListOf()

    list.addAll(this.damageRelations.doubleDamageFrom.map {
        TypeDataDamageRef(
            it.url.filter { it.isDigit() }.drop(1).toInt(),
            this.id,
            4
        )
    })
    list.addAll(this.damageRelations.halfDamageFrom.map {
        TypeDataDamageRef(
            it.url.filter { it.isDigit() }.drop(1).toInt(),
            this.id,
            1
        )
    })
    list.addAll(this.damageRelations.noDamageFrom.map {
        TypeDataDamageRef(
            it.url.filter { it.isDigit() }.drop(1).toInt(),
            this.id,
            0
        )
    })
    for (i in 1..18) {
        if (list.find { it.attackType == i } == null) {
            list.add(
                TypeDataDamageRef(
                    i,
                    this.id,
                    2
                )
            )
        }
    }
    return list
}

data class DamageRelation(
    @ColumnInfo val name: String,
    @ColumnInfo var modifier: Int
) {
    operator fun times(other: DamageRelation): DamageRelation {
        return DamageRelation(name, modifier * other.modifier)
    }

    operator fun times(factor: Int): DamageRelation {
        return DamageRelation(name, modifier * factor)
    }
}


