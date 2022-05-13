package com.example.pokdexproject.data.pokemon

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemonData: PokemonData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pokemonData: List<PokemonData>)

    @Update
    suspend fun updatePokemon(pokemonData: PokemonData)

    @Delete
    suspend fun deletePokemon(pokemonData: PokemonData)

    @Query("SELECT * from Pokemon")
    fun getPokemon(): Flow<List<PokemonData>>
}