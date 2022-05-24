package com.example.pokdexproject.activities.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonEvolutionModel
import com.example.pokdexproject.model.asEvolutionModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "PDP.DVM"

class DetailViewModel(val id: Int, application: Application) : ViewModel() {


    private val pokemonRepository = PokemonRepository(getDatabase(application))

    var basics: LiveData<PokemonData> = pokemonRepository.getBasics(id)
    val imageNr = MutableLiveData(0)
    val images: LiveData<List<String>> = pokemonRepository.getImages(id)
    val details: LiveData<DetailsData> = pokemonRepository.getDetails(id)
    val abilities: LiveData<List<AbilityData>> = pokemonRepository.getAbilities(id)

    val lineage: LiveData<List<PokemonData>> = pokemonRepository.getLineage(id)
    val evolutionChain: LiveData<List<PokemonEvolutionModel>> =
        Transformations.map(lineage) { data ->
            data.map { it.asEvolutionModel(id) }
        }

    var bookmarkXor = false
    var onTeamXor = false

    init {
        viewModelScope.launch {
            try {
                pokemonRepository.refreshDetails(id)
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            }
        }

    }

    fun toggleBookmark() {
        viewModelScope.launch {
            pokemonRepository.updatePokemon(
                basics.value!!.apply { isBookmarked = !isBookmarked }
            )
        }
        bookmarkXor != bookmarkXor
    }

    fun toggleOnTeam() {
        viewModelScope.launch {
            pokemonRepository.updatePokemon(
               basics.value!!.apply { isOnTeam = !isOnTeam }
            )
        }
        onTeamXor != onTeamXor
    }

}