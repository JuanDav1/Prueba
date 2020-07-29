package com.example.prueba.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable




    data class Collection (

@SerializedName("href") val href : String,
@SerializedName("version") val version : Double,
@SerializedName("links") val links : List<LinkX>,
@SerializedName("metadata") val metadata : Metadata,
@SerializedName("items") val items : List<Item>

) :Serializable