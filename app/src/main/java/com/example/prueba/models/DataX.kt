package com.example.prueba.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DataX(
    @SerializedName("nasa_id") val nasaId: String,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("keywords") val keywords: List<String>,
    @SerializedName("title") val title: String,
    @SerializedName("center") val center: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: String?,
    @SerializedName("photographer") val photographer: String?

): Serializable