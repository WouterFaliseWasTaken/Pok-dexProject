package com.example.pokdexproject.activities.onTeam

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


class OnTeamViewModel(application: Application) : ViewModel() {

    companion object {
        val factory = singleArgViewModelFactory(::OnTeamViewModel)
    }

    private val pokemonRepository = PokemonRepository(getDatabase(application))
    val pokemon: LiveData<List<PokemonModel>> = pokemonRepository.getOnTeamPokemon()

    init {
        refreshPokemonFromRepository()
    }

    private fun refreshPokemonFromRepository() {
        viewModelScope.launch {
            pokemonRepository.refreshPokemon()
        }
    }
}
