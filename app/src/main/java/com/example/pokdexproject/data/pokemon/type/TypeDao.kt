package com.example.pokdexproject.data.pokemon.type

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM Type WHERE id = " +"(SELECT attackType FROM DealsDamageTo WHERE ((defenseType = :defenseTypeid) AND (damageModifier = :modifier)))")
    fun getDamageTypes(defenseTypeid:Int,modifier:Int): Flow<List<TypeData>>


}