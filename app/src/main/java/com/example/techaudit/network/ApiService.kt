package com.example.techaudit.network

import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.Laboratorio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService{

    @POST("equipos")
    suspend fun enviarEquipo(
        @Body item: AuditItem
    ): Response<AuditItem>

    @POST("laboratorios")
    suspend fun enviarLaboratorio(
        @Body laboratorio: Laboratorio
    ): Response<Laboratorio>
}