package com.example.pokdexproject.data.pokemon.pokemonDetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokdexproject.model.LANGUAGE
import com.example.pokdexproject.network.PokemonDetailApiData
import com.example.pokdexproject.network.PokemonSpeciesApiData


@Entity(tableName = "PokemonDetails")
data class DetailsData(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo val flavorText: String = "",
    @ColumnInfo val height: Int = 0,
    @ColumnInfo val weight: Int = 0,
    @ColumnInfo val category: String = "",
    @ColumnInfo val genderRatio: Int = 4,
    @ColumnInfo val hp: Int = 0,
    @ColumnInfo val attack: Int = 0,
    @ColumnInfo val defense: Int = 0,
    @ColumnInfo val spattack: Int = 0,
    @ColumnInfo val spdeffense: Int = 0,
    @ColumnInfo val speed: Int = 0,
    @ColumnInfo val evolvesFromId: Int? = 0
)

fun getDatabaseModel(pDAP: PokemonDetailApiData, pSAP: PokemonSpeciesApiData): DetailsData {
    return DetailsData(
        pDAP.id,
        pSAP.flavorTextEntries.filter { it.language.name == LANGUAGE }[0].flavorText.replace(
            "\n",
            " "
        ),
        pDAP.height,
        pDAP.weight,
        pSAP.genera.filter { it.language.name == LANGUAGE }[0].genus,
        pSAP.genderRate,
        pDAP.stats[0].baseStat,
        pDAP.stats[1].baseStat,
        pDAP.stats[2].baseStat,
        pDAP.stats[3].baseStat,
        pDAP.stats[4].baseStat,
        pDAP.stats[5].baseStat,
        if (!pSAP.evolvesFromSpecies?.url?.filter { it.isDigit() }.isNullOrBlank()) {
            pSAP.evolvesFromSpecies!!.url.filter { it.isDigit() }.drop(1).toInt()
        } else null
    )
}