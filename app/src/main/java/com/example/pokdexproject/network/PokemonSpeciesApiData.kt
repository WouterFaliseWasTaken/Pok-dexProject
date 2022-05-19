package com.example.pokdexproject.network

import com.squareup.moshi.Json

//API Deserialized using https://jsonformatter.org/json-to-kotlin

data class PokemonSpeciesApiData (
    @Json(name = "base_happiness")
    val baseHappiness: Int,
    @Json(name = "capture_rate")
    val captureRate: Int,
    val color: Color,
    @Json(name = "egg_groups")
    val eggGroups: List<Color>,
    @Json(name = "evolution_chain")
    val evolutionChain: EvolutionChain,
    @Json(name = "evolves_from_species")
    val evolvesFromSpecies: Color,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>,
    @Json(name = "form_descriptions")
    val formDescriptions: List<Any?>,
    @Json(name = "forms_switchable")
    val formsSwitchable: Boolean,
    @Json(name = "gender_rate")
    val genderRate: Int,
    val genera: List<Genus>,
    val generation: Color,
    @Json(name = "growth_rate")
    val growthRate: Color,
    val habitat: Color,
    @Json(name = "has_gender_differences")
    val hasGenderDifferences: Boolean,
    @Json(name = "hatch_counter")
    val hatchCounter: Int,
    val id: Int,
    @Json(name = "is_baby")
    val isBaby: Boolean,
    @Json(name = "is_legendary")
    val isLegendary: Boolean,
    @Json(name = "is_mythical")
    val isMythical: Boolean,
    val name: String,
    val names: List<Name>,
    val order: Int,
    @Json(name = "pal_park_encounters")
    val palParkEncounters: List<PalParkEncounter>,
    @Json(name = "pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>,
    val shape: Color,
    val varieties: List<Variety>
)

data class Color (
    val name: String,
    val url: String
)

data class EvolutionChain (
    val url: String
)

data class FlavorTextEntry (
    @Json(name = "flavor_text")
    val flavorText: String,
    val language: Color,
    val version: Color
)

data class Genus (
    val genus: String,
    val language: Color
)

data class Name (
    val language: Color,
    val name: String
)

data class PalParkEncounter (
    val area: Color,
    @Json(name = "base_score")
    val baseScore: Int,
    val rate: Int
)

data class PokedexNumber (
    @Json(name = "entry_number")
    val entryNumber: Int,
    val pokedex: Color
)

data class Variety (
    @Json(name = "is_default")
    val isDefault: Boolean,
    val pokemon: Color
)
