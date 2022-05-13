package com.example.pokdexproject.network

data class PokemonApiData(
    val id: Int,
    val name: String,
    val sprites: PokemonImage,
    val types: List<TypeData>
) {
    fun getIdString(): String {
        var idString = "Nr. "
        when (id) {
            in (0..9) -> idString += "00"
            in (10..99) -> idString += "0"
            else -> Unit
        }
        return idString + id.toString()
    }

    fun capitaliseName(): String {
        return this.name[0].uppercase() + this.name.substring(1).lowercase()
    }
}

data class PokemonImage(
    val front_default: String
)

data class TypeData(
    val slot: Int,
    val type: TypeName,
)

data class TypeName(
    val name: String
)



