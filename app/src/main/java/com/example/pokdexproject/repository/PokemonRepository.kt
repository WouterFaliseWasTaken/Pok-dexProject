package com.example.pokdexproject.repository

import android.app.DownloadManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.pokdexproject.activities.main.Criterion
import com.example.pokdexproject.activities.main.FilterByTag
import com.example.pokdexproject.activities.main.QueryParemeters
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

    fun getPokemon(
        pm:QueryParemeters
    ): LiveData<List<PokemonModel>> {
        val search = if (pm.search.isNullOrBlank()) {
            "%"
        } else "%" + pm.search + "%"
        val daoResponse = database.pokemonDao().run {
            when (pm.filterByTag) {
                FilterByTag.NONE -> when (pm.sortByDirection.first) {
                    Criterion.NAME -> when (pm.sortByDirection.second) {
                        true -> this.getPokemonSortedByNameAsc(search)
                        false -> this.getPokemonSortedByNameDesc(search)
                    }
                    Criterion.ID -> when (pm.sortByDirection.second) {
                        true -> this.getPokemonSortedByIdAsc(search)
                        false -> this.getPokemonSortedByIdDesc(search)
                    }
                }
                FilterByTag.ONTEAM -> this.getOnTeamPokemon()
                FilterByTag.BOOKMARK -> this.getBookmarkedPokemon()
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
}