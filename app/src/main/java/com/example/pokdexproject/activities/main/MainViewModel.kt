package com.example.pokdexproject.activities.main

import android.app.Application
import androidx.lifecycle.*
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "PDP.MVM"

class MainViewModel(application: Application) : ViewModel() {

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    private val pokemonRepository = PokemonRepository(getDatabase(application))
    private val filter = FilterByTag.NONE


    var queryParameters = MutableLiveData(
        QueryParemeters(
            "",
            (Pair(Criterion.ID, true)),
            FilterByTag.NONE
        )
    )

    fun setSearch(search:String){
        var queryParams = queryParameters.value
        queryParams?.search = search
        queryParameters.value = queryParams
    }

    fun setSortBy(criterion: Pair<Criterion, Boolean>){
        var queryParams = queryParameters.value
        queryParams?.sortByDirection = criterion
        queryParameters.value = queryParams
    }

    var pokemon: LiveData<List<PokemonModel>> = Transformations.switchMap(queryParameters) { parameters ->
            pokemonRepository.getPokemon(parameters)
        }


    init {
        refreshPokemonFromRepository()
    }


    private fun refreshPokemonFromRepository() {
        viewModelScope.launch {
            try {
                pokemonRepository.refreshPokemon()
            } catch (e: IOException) {
            }
        }

    }

    class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}

enum class FilterByTag {
    NONE, ONTEAM, BOOKMARK
}

enum class Criterion {
    ID, NAME
}

data class QueryParemeters(
    var search: String,
    var sortByDirection: Pair<Criterion, Boolean>,
    var filterByTag: FilterByTag
)