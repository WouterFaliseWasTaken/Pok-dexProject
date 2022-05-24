package com.example.pokdexproject.activities.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData
import com.example.pokdexproject.data.pokemon.pokemonDetails.image.ImageData
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonEvolutionModel
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.model.asEvolutionModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

const val TAG = "PDP.DVM"

class DetailViewModel(id: Int, application: Application) : ViewModel() {

    private val pokemonRepository = PokemonRepository(getDatabase(application))

    val basics: LiveData<PokemonData> = pokemonRepository.getBasics(id)
    val imageNr = MutableLiveData<Int>(0)
    val images: LiveData<List<String>> = pokemonRepository.getImages(id)
    val details: LiveData<DetailsData> = pokemonRepository.getDetails(id)
    val abilities: LiveData<List<AbilityData>> = pokemonRepository.getAbilities(id)

    val lineage: LiveData<List<PokemonData>> = pokemonRepository.getLineage(id)
    val evolutionChain: LiveData<List<PokemonEvolutionModel>> =
        Transformations.map(lineage) { data ->
            data.map{it.asEvolutionModel(id)}
        }

    init {
        viewModelScope.launch {
            try {
                pokemonRepository.refreshDetails(id)
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            }
        }

    }

    //var displayedImage:String? = pokemon.images?.get(imageNr.value!!) ?: ""



}