package com.example.pokdexproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.pokdexproject.activities.main.QueryParemeters
import com.example.pokdexproject.data.pokemon.asDatabaseModel
import com.example.pokdexproject.database.PokemonRoomDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.model.asDomainModel
import com.example.pokdexproject.network.PokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepository(private val database: PokemonRoomDatabase) {

    fun getPokemon(
        pm: QueryParemeters
    ): LiveData<List<PokemonModel>> {
        val search = if (pm.search.isNullOrBlank()) {
            "%"
        } else "%" + pm.search + "%"
        val types = pm.typeIncluded.filter { it.value }.keys.toList().map { it.lowercase() }
        val query =
            "SELECT * from Pokemon WHERE((type1 in (" +
                    bindTypes(types) +
                    "))OR(type2 IN (" +
                    bindTypes(types) +
                    ")))AND name LIKE ? ORDER BY " +
                    pm.sortByDirection.first.column + pm.sortByDirection.second.direction
        val bindArgs = listOf(types, types).flatten().plus(search).toTypedArray()
        val call = SimpleSQLiteQuery(query, bindArgs)
        return database.pokemonDao().getPokemonDynamic(call)
            .map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }

    suspend fun refreshPokemon() {
        withContext(Dispatchers.IO) {
            val pokemon = PokeApi.PokeApiService.PokeApi.retrofitService.getBasicInfo()
            database.pokemonDao().insertAll((pokemon.map { it.asDatabaseModel() }))
        }
    }

    suspend fun refreshDetails() {
        withContext(Dispatchers.IO) {


        }
    }

    fun getOnTeamPokemon(): LiveData<List<PokemonModel>> {
        return database.pokemonDao().getOnTeamPokemon()
            .map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }

    fun getBookmarkedPokemon(): LiveData<List<PokemonModel>> {
        return database.pokemonDao().getBookmarkedPokemon()
            .map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }
}

private fun bindTypes(types: List<String>): String {
    var response: String
    if (types.isNotEmpty()) {
        response = "?"
        for (i: Int in 2..types.size) {
            response += ",?"
        }
        return response
    }
    return ""
}
