package com.example.pokdexproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokdexproject.data.DealsModifiedDamageTo
import com.example.pokdexproject.data.pokemon.PokemonDao
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.type.TypeDao
import com.example.pokdexproject.data.type.TypeData
import com.example.pokdexproject.data.type.dealsmodifieddamageto.DealsModifiedDamageToDao

@Database(
    entities = [PokemonData::class, TypeData::class, DealsModifiedDamageTo::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun dealsModifiedDamageToDao(): DealsModifiedDamageToDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDatabase(context: Context): PokemonRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonRoomDatabase::class.java,
                    "pokemon_database",
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }

}