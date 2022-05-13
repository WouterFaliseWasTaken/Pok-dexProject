package com.example.pokdexproject.data.type

import androidx.room.*

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertType(typeData: TypeData)

    @Update
    suspend fun updateType(typeData: TypeData)

    @Delete
    suspend fun deleteType(typeData: TypeData)
}