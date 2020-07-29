package com.example.prueba.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Int
): Serializable