package com.example.pokdexproject.data.pokemon.type

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertType(typeData: TypeData)

    @Update
    suspend fun updateType(typeData: TypeData)

    @Delete
    suspend fun deleteType(typeData: TypeData)
}

@Dao
interface TypeDataDamageRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelations(typeDataDamageRef: List<TypeDataDamageRef>)

    @Query(
        "SELECT Type.name as name, DealsDamageTo.damageModifier as modifier FROM Type, DealsDamageTo " +
                "WHERE (Type.id = DealsDamageTo.attackType) AND (DealsDamageTo.defenseType IN " +
                "(SELECT id FROM Type WHERE name IN (:name))" +
                ") ORDER BY name"
    )
    fun getRelations(name: List<String?>): LiveData<List<DamageRelation>>

}