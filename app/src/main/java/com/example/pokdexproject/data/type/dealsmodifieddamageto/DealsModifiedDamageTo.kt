package com.example.pokdexproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "DealsModifiedDamageTo", primaryKeys = ["typeAttacker", "typeDefender"])
class DealsModifiedDamageTo(
    @ColumnInfo val typeAttacker: String,//this is the type of the attack
    @ColumnInfo val typeDefender: String,//this is one of the two types of the defense
    @ColumnInfo val modifier: Double = 1.0 //This is either 0, 0.5, 1.0, or 2.0.
)