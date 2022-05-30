package com.example.pokdexproject.activities.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData
import com.example.pokdexproject.data.pokemon.type.DamageRelation
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonEvolutionModel
import com.example.pokdexproject.model.Type
import com.example.pokdexproject.model.asEvolutionModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "PDP.DVM"

class DetailViewModel(val id: Int, application: Application) : ViewModel() {


    private val pokemonRepository = PokemonRepository(getDatabase(application))

    val basics: LiveData<PokemonData> = pokemonRepository.getBasics(id)
    val imageNr = MutableLiveData(0)
    val images: LiveData<List<String>> = pokemonRepository.getImages(id)
    val details: LiveData<DetailsData> = pokemonRepository.getDetails(id)
    val abilities: LiveData<List<AbilityData>> = pokemonRepository.getAbilities(id)
    private val lineage: LiveData<List<PokemonData>> = pokemonRepository.getLineage(id)
    val evolutionChain: LiveData<List<PokemonEvolutionModel>> =
        Transformations.map(lineage) { data ->
            data.map { it.asEvolutionModel(id) }
        }
    val typeDamageList: LiveData<List<DamageRelation>> =
        Transformations.switchMap(basics) { basics ->
            pokemonRepository.getTypeRelations(
                listOf(
                    basics.type1,
                    basics.type2
                )
            )
        }

    val typeDamageRelations: List<List<Type>>
        get() {
            //ONLY CALL WHEN OBSERVED!!
            val combinedList: List<DamageRelation>?
            if (basics.value!!.type2 == null) {
                combinedList = typeDamageList.value!!.map { (it * 2) }
            } else {
                val tempList = mutableListOf<DamageRelation>()
                for (i in 0 until typeDamageList.value!!.size / 2) {
                    tempList += (typeDamageList.value!![2 * i]) * (typeDamageList.value!![2 * i+1])
                }
                combinedList = tempList
            }
            Log.d(TAG, combinedList.toString())
            val (noDamageList, rest1) = combinedList.partition { it.modifier == 0 }
            val (quarterDamageList, rest2) = rest1.partition { it.modifier == 1 }
            val (halfDamageList, rest3) = rest2.partition { it.modifier == 2 }
            val (fullDamageList, rest4) = rest3.partition { it.modifier == 4 }
            val (doubleDamageList, rest5) = rest4.partition { it.modifier == 8 }
            val (quadrupleDamageList, errorList) = rest5.partition { it.modifier == 16 }
            //did kan beter met custom partition functie(indien lijst gesoorteerd is)
            if (errorList.isNotEmpty()) Log.e(TAG, "Type Damage value outside of expected values!")
            return listOf(
                noDamageList,
                quarterDamageList,
                halfDamageList,
                fullDamageList,
                doubleDamageList,
                quadrupleDamageList
            ).map {
                it.map {
                    Type.valueOf(it.name.uppercase())
                }
            }
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

    fun toggleBookmark() {
        viewModelScope.launch {
            pokemonRepository.updatePokemon(
                basics.value!!.apply { isBookmarked = !isBookmarked }
            )
        }
    }

    fun toggleOnTeam() {
        viewModelScope.launch {
            pokemonRepository.updatePokemon(
                basics.value!!.apply { isOnTeam = !isOnTeam }
            )
        }
    }
}