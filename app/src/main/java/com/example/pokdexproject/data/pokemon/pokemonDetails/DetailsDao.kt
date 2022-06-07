package com.example.pokdexproject.data.pokemon.pokemonDetails

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(detailsData: DetailsData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDetails(detailsData: List<DetailsData>)

    @Query("SELECT * from PokemonDetails WHERE id = :id")
    fun getPokemonDetails(id: Int): LiveData<DetailsData>

    @Query("SELECT id from PokemonDetails WHERE evolvesFromId = :id")
    fun getEvolveds(id: Int): List<Int>
}