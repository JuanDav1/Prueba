package com.example.prueba.repositories

import com.example.prueba.api.CallsAPi
import com.example.prueba.api.OperationResult
import com.example.prueba.models.Data

class RemoteRepository(private val callsApi: CallsAPi) {

    suspend fun getCollection(): OperationResult<Data> {

        try {
            val response = callsApi.getCollection()
            response.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()
                    OperationResult.Success(data)
                } else {
                    OperationResult.Error(Exception("Ocurrió un error"))
                }
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}
