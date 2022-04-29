package com.example.pokdexproject.data

import android.util.Log
import com.example.pokdexproject.model.Pokémon
import com.example.pokdexproject.model.PokémonType
import com.example.pokdexproject.model.TypeEnum

class TestDataSource {

    fun getListOfPokémon(): MutableList<Pokémon> {
        val list: MutableList<Pokémon> = mutableListOf()
        for (i: Int in 1..20) {
            list.add(
                Pokémon(
                    1,
                    "Bulbasaur",
                    PokémonType(TypeEnum.Grass),
                    PokémonType(TypeEnum.Poison),
                    listOf(),
                    listOf(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                )
            )
        }

        Log.d("debug",list.size.toString())
        return list
    }
}