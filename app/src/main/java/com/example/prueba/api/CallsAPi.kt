package com.example.prueba.api

import com.example.prueba.models.Collection
import com.example.prueba.models.Data
import retrofit2.Response
import retrofit2.http.GET

interface CallsAPi {

    @GET("search?q=apollo%2011")
    suspend fun getCollection (): Response<Data>

}