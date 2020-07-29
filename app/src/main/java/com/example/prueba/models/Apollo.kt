package com.example.prueba.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "Apollo")
data class Apollo(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val tittle: String,
    val dataCreate: String,
    val description: String,
    val nasaId: String,
    val mediaType: String,
    val isFavorite: Boolean
) : Serializable

