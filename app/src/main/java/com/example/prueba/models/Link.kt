package com.example.prueba.models



import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Link(

    @SerializedName("href") val href : String,
    @SerializedName("rel") val rel : String,
    @SerializedName("render") val render : String?

)
    : Serializable