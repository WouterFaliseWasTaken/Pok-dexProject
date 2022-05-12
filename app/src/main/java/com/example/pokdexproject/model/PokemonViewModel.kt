package com.example.pokdexproject.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdexproject.network.PokeApi
import com.example.pokdexproject.network.PokemonData
import kotlinx.coroutines.launch

const val TAG = "POKEMONDEBUG"

class PokemonViewModel : ViewModel() {
    private var _pokemon = MutableLiveData<List<PokemonData>>(mutableListOf<PokemonData>())
    val pokemon : LiveData<List<PokemonData>> =_pokemon
    private var _pokemonFiltered = MutableLiveData<List<PokemonData>>(mutableListOf<PokemonData>())
    val pokemonFiltered : LiveData<List<PokemonData>> =_pokemonFiltered

    init {
        getBasicInfo()
    }



    private fun getBasicInfo() {
        viewModelScope.launch {
            try {
                val listResult = PokeApi.PokeApiService.PokeApi.retrofitService.getBasicInfo()
                Log.d(TAG,listResult.toString())
                _pokemon.value = listResult
                _pokemonFiltered.value = listResult
            } catch (e: Exception) {
                Log.d(TAG, "LOADING DATA FAILED")
            }
        }
    }
}


//Consider combining the stats into a map of Key/value pairs.

/**
data class PokémonType(
    var type: String
    //add image reference here
) {
}



data class Ability(val name: String)
//todo

data class Move(val name: String)
//todo
*/