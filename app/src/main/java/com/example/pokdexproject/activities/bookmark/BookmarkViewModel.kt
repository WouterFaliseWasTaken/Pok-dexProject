package com.example.pokdexproject.activities.bookmark

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.io.IOException

const val TAG = "PDP.OTVM"

class BookmarkViewModel(application: Application) : ViewModel() {

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    private val pokemonRepository = PokemonRepository(getDatabase(application))

    val pokemon: LiveData<List<PokemonModel>> = pokemonRepository.getBookmarkedPokemon()

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

    class BookmarkViewModelFactory(val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}
