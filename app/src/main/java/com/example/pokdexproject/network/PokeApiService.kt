package com.example.pokdexproject.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object PokeApi {
    private const val BASE_URL =
        "https://stoplight.io/mocks/appwise-be/pokemon/57519009/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    interface PokeApiService {
        @GET("pokemon")
        suspend fun getBasicInfo(): List<PokemonApiData>

        object PokeApi {
            val retrofitService: PokeApiService by lazy {
                retrofit.create(PokeApiService::class.java)
            }
        }
    }
}

