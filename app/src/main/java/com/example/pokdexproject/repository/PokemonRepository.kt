package com.example.pokdexproject.repository

import android.util.Log
import com.example.pokdexproject.activities.main.TAG
import com.example.pokdexproject.data.pokemon.asDatabaseModel
import com.example.pokdexproject.database.PokemonRoomDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.model.asDomainModel
import com.example.pokdexproject.network.PokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepository(private val database: PokemonRoomDatabase) {
    val pokemon: Flow<List<PokemonModel>> =
        database.pokemonDao().getPokemon().map { value -> value.map { it.asDomainModel() } }

    suspend fun refreshPokemon() {
        withContext(Dispatchers.IO) {
            val pokemon = PokeApi.PokeApiService.PokeApi.retrofitService.getBasicInfo()
            database.pokemonDao().insertAll((pokemon.map { it.asDatabaseModel() }))
            Log.d(TAG, "Database refreshed")
        }
    }
}