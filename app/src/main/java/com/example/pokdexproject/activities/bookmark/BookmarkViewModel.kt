package com.example.pokdexproject.activities.bookmark

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokdexproject.commonCode.singleArgViewModelFactory
import com.example.pokdexproject.database.PokemonRoomDatabase.Companion.getDatabase
import com.example.pokdexproject.model.PokemonModel
import com.example.pokdexproject.repository.PokemonRepository
import kotlinx.coroutines.launch

const val TAG = "PDP.OTVM"

class BookmarkViewModel(application: Application) : ViewModel() {

    companion object {
        val factory = singleArgViewModelFactory(::BookmarkViewModel)
    }

    private val pokemonRepository = PokemonRepository(getDatabase(application))

    val pokemon: LiveData<List<PokemonModel>> = pokemonRepository.getBookmarkedPokemon()

    init {
        refreshPokemonFromRepository()
    }

    private fun refreshPokemonFromRepository() {
        viewModelScope.launch {
            pokemonRepository.refreshPokemon()
        }
    }


}
