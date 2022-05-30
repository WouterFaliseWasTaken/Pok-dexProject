import com.squareup.moshi.Json

data class TypeApiData (
    @Json(name = "damage_relations")
    val damageRelations: DamageRelations,

    @Json(name = "game_indices")
    val gameIndices: List<GameIndex>,

    val generation: Generation,
    val id: Int,
    @Json(name = "move_damage_class")
    val moveDamageClass: Generation?,

    val moves: List<Generation>,
    val name: String,
    val names: List<Name>,

    @Json(name = "past_damage_relations")
    val pastDamageRelations: List<PastDamageRelation>,
    val pokemon: List<Pokemon>
)

data class DamageRelations (
    @Json(name = "double_damage_from")
    val doubleDamageFrom: List<Generation>,

    @Json(name = "double_damage_to")
    val doubleDamageTo: List<Generation>,

    @Json(name = "half_damage_from")
    val halfDamageFrom: List<Generation>,

    @Json(name = "half_damage_to")
    val halfDamageTo: List<Generation>,

    @Json(name = "no_damage_from")
    val noDamageFrom: List<Generation>,

    @Json(name = "no_damage_to")
    val noDamageTo: List<Generation>
)

data class Generation (
    val name: String,
    val url: String
)

data class GameIndex (
    @Json(name = "game_index")
    val gameIndex: Int,

    val generation: Generation
)

data class Name (
    val language: Generation,
    val name: String
)

data class PastDamageRelation (
    @Json(name = "damage_relations")
    val damageRelations: DamageRelations,

    val generation: Generation
)

data class Pokemon (
    val pokemon: Generation,
    val slot: Int
)
