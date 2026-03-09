package com.example.techaudit.data

import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow
import com.example.techaudit.network.RetrofitClient

class AuditRepository (private val auditDao: AuditDao) {

    // ======================
    // EQUIPOS
    // ======================
    val allItems: Flow<List<AuditItem>> = auditDao.getAllItems()

    val allLaboratorios = auditDao.getLaboratorios()

    suspend fun insert(item: AuditItem){
        auditDao.insert(item)
    }

    suspend fun update(item: AuditItem){
        auditDao.update(item)
    }

    suspend fun delete(item: AuditItem){
        auditDao.delete(item)
    }

    // ======================
    // LABORATORIOS
    // ======================

    suspend fun insertLaboratorio(lab: Laboratorio){
        auditDao.insertLaboratorio(lab)
    }

    fun getEquiposByLaboratorio(labId: Int) =
        auditDao.getEquiposByLaboratorio(labId)

    suspend fun sincronizarDatos(){
        try{
            //Enviar laboratorios
            val laboratorios = auditDao.getLaboratoriosList()
            for(lab in laboratorios){
                RetrofitClient.api.enviarLaboratorio(lab)
            }

            //Enviar equipos
            val equipos = auditDao.getEquiposList()
            for(equipo in equipos){
                RetrofitClient.api.enviarEquipo(equipo)
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

}