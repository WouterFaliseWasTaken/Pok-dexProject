package com.example.pokdexproject.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdexproject.network.PokeApiService
import com.example.pokdexproject.network.PokemonData
import kotlinx.coroutines.launch

const val TAG = "POKEMONDEBUG"

class PokémonViewModel : ViewModel() {
    /**
    private val _index = MutableLiveData<Int>(0)
    val index: LiveData<Int> = _index
    private val _name = MutableLiveData<String>("")
    val name: LiveData<String> = _name

    //add image reference here = MutableLiveData<>()
    //add image reference here :LiveData<> =
    private val _type1 = MutableLiveData<PokémonType>(PokémonType(""))
    val type1: LiveData<PokémonType> = _type1
    private val _type2 = MutableLiveData<PokémonType?>(PokémonType(""))
    val type2: LiveData<PokémonType?> = _type2
*/
    private var _pokémon = MutableLiveData<List<PokemonData>>(mutableListOf<PokemonData>())
    val pokémon : LiveData<List<PokemonData>> =_pokémon
    /**
    private val _abilities = MutableLiveData<List<Ability>>(mutableListOf<Ability>())
    private val _moveset = MutableLiveData<List<Move>>(mutableListOf<Move>())
    private val _bookmarked = MutableLiveData<Boolean>(false)
    private val _onTeam = MutableLiveData<Boolean>(false)
    private val _health = MutableLiveData<Int>(0)
    private val _attack = MutableLiveData<Int>(0)
    private val _defense = MutableLiveData<Int>(0)
    private val _specialAttack = MutableLiveData<Int>(0)
    private val _specialDefense = MutableLiveData<Int>(0)
    private val _speed = MutableLiveData<Int>(0)
    */
    init {
        getBasicInfo()
    }


    private fun getBasicInfo() {
        viewModelScope.launch {
            try {
                val listResult = PokeApiService.PokeApi.retrofitService.getBasicInfo()
                Log.d(TAG,listResult.toString())
                _pokémon.value = listResult
                Log.d(TAG, "_pokemon has ${_pokémon.value!!.size}")
                Log.d(TAG, "list_result has ${listResult.size}")
            } catch (e: Exception) {
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