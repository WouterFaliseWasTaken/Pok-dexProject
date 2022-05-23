package com.example.pokdexproject.data.pokemon

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
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

    @Query("SELECT * from Pokemon WHERE isBookmarked = 1")
    fun getBookmarkedPokemon(): Flow<List<PokemonData>>

    @Query("SELECT * from Pokemon WHERE isOnTeam = 1")
    fun getOnTeamPokemon(): Flow<List<PokemonData>>

    @RawQuery(observedEntities = [PokemonData::class])
    fun getPokemonDynamic(query:SimpleSQLiteQuery):Flow<List<PokemonData>>

    @Query("SELECT * from Pokemon WHERE id = :id")
    fun getSpecificPokemon(id:Int): LiveData<PokemonData>
}