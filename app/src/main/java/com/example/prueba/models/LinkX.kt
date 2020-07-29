package com.example.prueba.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class LinkX(
    @SerializedName("href") val href : String,
    @SerializedName("rel") val rel : String,
    @SerializedName("prompt") val prompt: String
): Serializable