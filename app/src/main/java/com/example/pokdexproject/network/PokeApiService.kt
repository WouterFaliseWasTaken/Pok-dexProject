package com.example.pokdexproject.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://stoplight.io/mocks/appwise-be/pokemon/32428517/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface PokeApiService {
    // TODO: Fetch basic pokémon values for 150 pokémon
    @GET("pokemon")
    suspend fun getBasicInfo() : List<PokemonData>
    object PokeApi{
        val retrofitService : PokeApiService by lazy{
            retrofit.create(PokeApiService::class.java)
        }
    }

}

