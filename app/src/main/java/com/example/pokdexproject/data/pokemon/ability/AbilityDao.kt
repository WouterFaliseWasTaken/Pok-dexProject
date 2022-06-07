package com.example.pokdexproject.data.pokemon.ability

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbility(abilityData: AbilityData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAbilities(abilityData: List<AbilityData>)

    @Query(
        "SELECT * from Ability WHERE id IN" +
                " (SELECT abilityId from HasAbility WHERE ownerId = :id)"
    )
    fun getAbilities(id: Int): LiveData<List<AbilityData>>

}

@Dao
interface AbilityDataCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRelations(abilityDataCrossRef: List<AbilityDataCrossRef>)

}