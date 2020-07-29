package com.example.prueba.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class DataApollo(

    val id: Int,
    val tittle: String,
    val dataCreate: String,
    val description: String,
    val nasaId: String,
    val mediaType: String
    ): Serializable
