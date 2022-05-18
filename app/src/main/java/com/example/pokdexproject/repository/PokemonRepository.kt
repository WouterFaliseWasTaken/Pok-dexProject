package com.example.pokdexproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.pokdexproject.activities.main.Criterion
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
        val types = pm.typeIncluded.filter{it.value}.keys.toList().map{it.lowercase()}
        val daoResponse = database.pokemonDao().run {
            when (pm.sortByDirection.first) {
                Criterion.NAME -> when (pm.sortByDirection.second) {
                    true -> this.getPokemonSortedByNameAsc(search,types)
                    false -> this.getPokemonSortedByNameDesc(search,types)
                }
                Criterion.ID -> when (pm.sortByDirection.second) {
                    true -> this.getPokemonSortedByIdAsc(search,types)
                    false -> this.getPokemonSortedByIdDesc(search,types)
                }
            }
        }
        return daoResponse.map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }

    suspend fun refreshPokemon() {
        withContext(Dispatchers.IO) {
            val pokemon = PokeApi.PokeApiService.PokeApi.retrofitService.getBasicInfo()
            database.pokemonDao().insertAll((pokemon.map { it.asDatabaseModel() }))
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
