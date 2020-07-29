package com.example.prueba.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Data(
    @SerializedName("collection")
    val collection: Collection
): Serializable