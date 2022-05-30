package com.example.pokdexproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.pokdexproject.activities.main.QueryParemeters
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.ability.asAbilityData
import com.example.pokdexproject.data.pokemon.ability.asAbilityRelations
import com.example.pokdexproject.data.pokemon.asDatabaseModel
import com.example.pokdexproject.data.pokemon.move.asMoveData
import com.example.pokdexproject.data.pokemon.move.asMoveRelations
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData
import com.example.pokdexproject.data.pokemon.pokemonDetails.getDatabaseModel
import com.example.pokdexproject.data.pokemon.pokemonDetails.image.asImageData
import com.example.pokdexproject.data.pokemon.type.DamageRelation
import com.example.pokdexproject.data.pokemon.type.TypeDataDamageRefDao
import com.example.pokdexproject.data.pokemon.type.toDatabaseModel
import com.example.pokdexproject.data.pokemon.type.toTypeModel
import com.example.pokdexproject.database.PokemonRoomDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.model.asDomainModel
import com.example.pokdexproject.network.PokeApi
import com.example.pokdexproject.network.PokeDetailsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

const val TAG = "REPOSITORY"

class PokemonRepository(private val database: PokemonRoomDatabase) {

    fun getPokemon(
        pm: QueryParemeters
    ): LiveData<List<PokemonModel>> {
        var search = "%"
        if (!pm.search.isNullOrBlank()) search += pm.search + "%"
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

    suspend fun refreshDetails(id: Int) {
        withContext(Dispatchers.IO) {
            val details = PokeDetailsApi.PokeApiService.PokeApi.retrofitService.getDetails(id)
            val species = PokeDetailsApi.PokeApiService.PokeApi.retrofitService.getSpeciesInfo(id)
            database.detailsDao().insertDetails(getDatabaseModel(details, species))
            database.imageDao().insertAllImages(details.asImageData())
            database.abilityDao().insertAllAbilities(details.asAbilityData())
            database.abilityDataCrossRefDao().insertAllRelations(details.asAbilityRelations())
            database.moveDao().insertAllMoves(details.asMoveData())
            database.moveDataCrossRefDao().insertAllRelations(details.asMoveRelations())
        }
    }

    suspend fun refreshTypeAdvantages(i: Int) {
        withContext(Dispatchers.IO) {
            val apiData = PokeDetailsApi.PokeApiService.PokeApi.retrofitService.getTypeInfo(i)
            database.typeDao().insertType(apiData.toTypeModel())
            database.typeDataDamageRefDao().insertRelations(apiData.toDatabaseModel())
        }
    }

    suspend fun updatePokemon(data: PokemonData) {
        database.pokemonDao().updatePokemon(data)
    }

    fun getOnTeamPokemon(): LiveData<List<PokemonModel>> {
        return database.pokemonDao().getOnTeamPokemon()
            .map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }

    fun getBookmarkedPokemon(): LiveData<List<PokemonModel>> {
        return database.pokemonDao().getBookmarkedPokemon()
            .map { value -> value.map { it.asDomainModel() } }.asLiveData()
    }

    fun getBasics(id: Int): LiveData<PokemonData> {
        return database.pokemonDao().getSpecificPokemon(id)
    }

    fun getDetails(id: Int): LiveData<DetailsData> {
        return database.detailsDao().getPokemonDetails(id)
    }

    fun getImages(id: Int): LiveData<List<String>> {
        return database.imageDao().getPokemonsImages(id)
    }

    fun getAbilities(id: Int): LiveData<List<AbilityData>> {
        return database.abilityDao().getAbilities(id)
    }

    fun getLineage(inputId: Int): LiveData<List<PokemonData>> {
        return database.pokemonDao().getPokemonLineage(inputId)
    }

     fun getTypeRelations(type: List<String?>): LiveData<List<DamageRelation>> {
        return database.typeDataDamageRefDao().getRelations(type)
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
