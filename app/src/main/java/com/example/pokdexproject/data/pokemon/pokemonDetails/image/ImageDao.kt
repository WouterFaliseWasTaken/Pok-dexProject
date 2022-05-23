package com.example.pokdexproject.data.pokemon.pokemonDetails.image

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageData: ImageData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(imageData: List<ImageData>)

    @Query("SELECT url from Image WHERE ownerId = :id")
    fun getPokemonsImages(id: Int): LiveData<List<String>>
}