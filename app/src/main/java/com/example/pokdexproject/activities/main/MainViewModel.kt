package com.example.pokdexproject.activities.main

import android.app.Application
import androidx.lifecycle.*
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.model.Type
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "PDP.MVM"

class MainViewModel(application: Application) : ViewModel() {

    private val pokemonRepository = PokemonRepository(getDatabase(application))

    var queryParameters = MutableLiveData(
        QueryParemeters(
            "",
            (Pair(Criterion.ID, Direction.ASCENDING)),
            mutableMapOf<String, Boolean>().apply {
                for (type in Type.values()) {
                    this += Pair(type.unCapsLock(), true)
                }
            }
        )
    )

    fun setSearch(search: String) {
        val queryParams = queryParameters.value
        queryParams?.search = search
        queryParameters.value = queryParams
    }

    fun setSortBy(criterion: Pair<Criterion, Direction>) {
        val queryParams = queryParameters.value
        queryParams?.sortByDirection = criterion
        queryParameters.value = queryParams
    }

    var pokemon: LiveData<List<PokemonModel>> =
        Transformations.switchMap(queryParameters) { parameters ->
            pokemonRepository.getPokemon(parameters)
        }

    init {
        refreshPokemonFromRepository()
        refreshTypeAdvantages()
    }

    private fun refreshTypeAdvantages() {
        viewModelScope.launch{
            for(i in 1 until Type.values().size){
                pokemonRepository.refreshTypeAdvantages(i)
            }
        }
    }


    private fun refreshPokemonFromRepository() {
        viewModelScope.launch {
            try {
                pokemonRepository.refreshPokemon()
            } catch (e: IOException) {
            }
        }

    }

    fun unselectAllTypes() {
        queryParameters.value!!.typeIncluded = mutableMapOf<String, Boolean>().apply {
            for (type in Type.values()) {
                this += Pair(type.unCapsLock(), false)
            }
        }
    }

    fun selectType(type: String) {
        val queryParams = queryParameters.value
        queryParams!!.typeIncluded[type] = true
        queryParameters.value = queryParams
    }

    fun refreshDetails() {
        viewModelScope.launch() {
            for (i: Int in 1..pokemon.value!!.size) {
                pokemonRepository.refreshDetails(i)
            }
        }
    }

    class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}

enum class Criterion(val column: String) {
    ID("id "), NAME("name ")
}

enum class Direction(val direction: String) {
    ASCENDING("ASC"), DESCENDING("DESC")
}

data class QueryParemeters(
    var search: String,
    var sortByDirection: Pair<Criterion, Direction>,
    var typeIncluded: MutableMap<String, Boolean>
)


