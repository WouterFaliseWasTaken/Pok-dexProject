package com.example.pokdexproject.data.pokemon.move

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMove(moveData: MoveData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoves(moveData: List<MoveData>)

    @Query("SELECT level FROM hasMove WHERE ownerId = :id ORDER BY moveId")
    fun getMoveLevels(id: Int): List<Int>

    @Query(
        "SELECT * FROM move WHERE id IN " +
                " (SELECT moveId From hasMove WHERE OwnerId = :id) ORDER BY id"
    )
    fun getMoves(id: Int): List<MoveData>

}

@Dao
interface MoveDataCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(moveDataCrossRef: MoveDataCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRelations(moveDataCrossRef: List<MoveDataCrossRef>)


}