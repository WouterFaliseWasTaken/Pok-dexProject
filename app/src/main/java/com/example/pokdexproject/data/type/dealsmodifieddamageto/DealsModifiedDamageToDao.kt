package com.example.pokdexproject.data.type.dealsmodifieddamageto

import androidx.room.*
import com.example.pokdexproject.data.type.dealsmodifieddamageto.DealsModifiedDamageTo

@Dao
interface DealsModifiedDamageToDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDealsModifiedDamageTo(dealsModifiedDamageto: DealsModifiedDamageTo)

    @Update
    suspend fun updateDealsModifiedDamageTo(dealsModifiedDamageto: DealsModifiedDamageTo)

    @Delete
    suspend fun deleteDealsModifiedDamageTo(dealsModifiedDamageto:DealsModifiedDamageTo)
}