package com.example.pokdexproject

import android.app.Application
import com.example.pokdexproject.database.PokemonRoomDatabase

class PokedexApplication : Application() {
    val database: PokemonRoomDatabase by lazy { PokemonRoomDatabase.getDatabase(this) }
}