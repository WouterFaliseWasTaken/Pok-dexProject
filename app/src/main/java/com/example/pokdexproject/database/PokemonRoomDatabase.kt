package com.example.pokdexproject.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokdexproject.data.pokemon.PokemonDao
import com.example.pokdexproject.data.pokemon.PokemonData
import com.example.pokdexproject.data.pokemon.ability.AbilityDao
import com.example.pokdexproject.data.pokemon.ability.AbilityData
import com.example.pokdexproject.data.pokemon.ability.AbilityDataCrossRef
import com.example.pokdexproject.data.pokemon.ability.AbilityDataCrossRefDao
import com.example.pokdexproject.data.pokemon.move.MoveDao
import com.example.pokdexproject.data.pokemon.move.MoveData
import com.example.pokdexproject.data.pokemon.move.MoveDataCrossRef
import com.example.pokdexproject.data.pokemon.move.MoveDataCrossRefDao
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsDao
import com.example.pokdexproject.data.pokemon.pokemonDetails.DetailsData
import com.example.pokdexproject.data.pokemon.pokemonDetails.image.ImageDao
import com.example.pokdexproject.data.pokemon.pokemonDetails.image.ImageData
import com.example.pokdexproject.data.pokemon.type.TypeDao
import com.example.pokdexproject.data.pokemon.type.TypeData
import com.example.pokdexproject.data.pokemon.type.TypeDataDamageRef
import com.example.pokdexproject.data.pokemon.type.TypeDataDamageRefDao
import com.example.pokdexproject.model.DetailedPokemonModel
import java.util.concurrent.Executors

const val TAG = "QUERY"

@Database(
    entities = [PokemonData::class, TypeData::class,DetailsData::class,
        ImageData::class,MoveData::class,AbilityData::class,
        TypeDataDamageRef::class,AbilityDataCrossRef::class,MoveDataCrossRef::class],
    version = 4,
    exportSchema = false
)
abstract class PokemonRoomDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun detailsDao(): DetailsDao
    abstract fun imageDao(): ImageDao
    abstract fun moveDao(): MoveDao
    abstract fun abilityDao(): AbilityDao
    abstract fun typeDataDamageRefDao(): TypeDataDamageRefDao
    abstract fun abilityDataCrossRefDao(): AbilityDataCrossRefDao
    abstract fun moveDataCrossRefDao(): MoveDataCrossRefDao


    companion object {
        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDatabase(context: Context): PokemonRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonRoomDatabase::class.java,
                    "pokemon_database",
                ).fallbackToDestructiveMigration()
                    .setQueryCallback({ sqlQuery, bindArgs ->
                        Log.d(TAG, ("SQL Query: $sqlQuery SQL Args: $bindArgs"))
                    }, Executors.newSingleThreadExecutor())
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

}