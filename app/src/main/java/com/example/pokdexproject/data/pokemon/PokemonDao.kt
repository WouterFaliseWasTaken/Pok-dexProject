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

    @Query("SELECT * FROM Pokemon")
    fun getPokemon(): Flow<List<PokemonData>>

    @Query("SELECT * FROM Pokemon WHERE isBookmarked = 1")
    fun getBookmarkedPokemon(): Flow<List<PokemonData>>

    @Query("SELECT * FROM Pokemon WHERE isOnTeam = 1")
    fun getOnTeamPokemon(): Flow<List<PokemonData>>

    @RawQuery(observedEntities = [PokemonData::class])
    fun getPokemonDynamic(query: SimpleSQLiteQuery): Flow<List<PokemonData>>

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getSpecificPokemon(id: Int): LiveData<PokemonData>

    @Query(
        "SELECT * FROM Pokemon WHERE id IN" +
                "((SELECT evolvesFromId FROM PokemonDetails WHERE id ="
                + "(SELECT evolvesFromId FROM PokemonDetails WHERE id = :id))" +
                ",(SELECT evolvesFromId FROM PokemonDetails WHERE id = :id)," +
                "(:id)" +
                ",(SELECT id FROM PokemonDetails WHERE evolvesFromId = :id)," +
                "(SELECT id FROM PokemonDetails WHERE evolvesFromId IN" +
                "(SELECT id FROM PokemonDetails WHERE evolvesFromId = :id)))"
    )
    fun getPokemonLineage(id: Int): LiveData<List<PokemonData>>

    @Query("SELECT Count() FROM Pokemon WHERE isOnTeam = 1")
    fun countOnTeamPokemon(): Flow<Int>

    @Query("SELECT Count() FROM Pokemon WHERE isBookmarked = 1")
    fun countBookmarkedPokemon(): Flow<Int>
}