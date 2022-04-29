package com.example.pokdexproject.data

import com.example.pokdexproject.model.Pokémon
import com.example.pokdexproject.model.PokémonType
import com.example.pokdexproject.model.TypeEnum

class TestDataSource {

    fun getListOfPokémon(): MutableList<Pokémon> {
        val list: MutableList<Pokémon> = mutableListOf()
        for (i: Int in 1..10) {
            list.add(
                Pokémon(
                    "#" + (i * 2 - 1),
                    "Bulbasaur",
                    PokémonType(TypeEnum.Grass),
                    PokémonType(TypeEnum.Poison),
                    listOf(),
                    listOf(),
                    false,
                    true,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                )
            )
            list.add(
                Pokémon(
                    ("#" + (i * 2).toString()),
                    "Charmander",
                    PokémonType(TypeEnum.Fire),
                    null,
                    listOf(),
                    listOf(),
                    true,
                    false,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                )
            )
        }
        return list
    }
}