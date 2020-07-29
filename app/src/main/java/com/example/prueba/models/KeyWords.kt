package com.example.prueba.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "KeyWords",
    indices = [Index("id_apollo")],
    foreignKeys = [ForeignKey(
        entity = Apollo::class,
        parentColumns =["id"],
        childColumns = ["id_apollo"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class KeyWords(
    @PrimaryKey val id: Int,
    val nasaID : String,
    val keyword: String,
    val id_apollo:Int
){




}
