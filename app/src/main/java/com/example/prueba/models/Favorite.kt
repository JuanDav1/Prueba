package com.example.prueba.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Favorite",
        indices = [androidx.room.Index("id_apollo")],
    foreignKeys = [ForeignKey(
        entity = Apollo::class,
        parentColumns =["id"],
        childColumns = ["id_apollo"],
        onDelete = ForeignKey.CASCADE
    )])
data class Favorite(
    @PrimaryKey
    val id_apollo: Int,
    val isFavorite : Boolean = false
):Serializable

