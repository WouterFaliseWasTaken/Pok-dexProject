package com.example.pokdexproject.data.pokemon

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

    @Query("SELECT * from Pokemon WHERE ((type1 IN (:types))OR(type2 IN(:types)))AND (name LIKE :search) ORDER BY name ASC")
    fun getPokemonSortedByNameAsc(search: String,types:List<String>): Flow<List<PokemonData>>

    @Query("SELECT * from Pokemon WHERE ((type1 IN (:types))OR(type2 IN(:types)))AND (name LIKE :search) ORDER BY name DESC")
    fun getPokemonSortedByNameDesc(search: String,types:List<String>): Flow<List<PokemonData>>

    @Query("SELECT * from Pokemon WHERE ((type1 IN (:types))OR(type2 IN(:types)))AND (name LIKE :search) ORDER BY id ASC")
    fun getPokemonSortedByIdAsc(search: String,types:List<String>): Flow<List<PokemonData>>

    @Query("SELECT * from Pokemon WHERE ((type1 IN (:types))OR(type2 IN(:types)))AND (name LIKE :search) ORDER BY id DESC")
    fun getPokemonSortedByIdDesc(search: String,types:List<String>): Flow<List<PokemonData>>

    @RawQuery(observedEntities = [PokemonData::class])
    fun getPokemonDynamic(query:SimpleSQLiteQuery):Flow<List<PokemonData>>

}