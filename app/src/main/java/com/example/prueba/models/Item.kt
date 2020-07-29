package com.example.prueba.models


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Item(
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("data")
    val data: List<DataX>

): Serializable