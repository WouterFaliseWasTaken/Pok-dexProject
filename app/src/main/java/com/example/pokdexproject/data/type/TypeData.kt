package com.example.pokdexproject.data.type


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Type")
class TypeData (
    @PrimaryKey val name:String
)