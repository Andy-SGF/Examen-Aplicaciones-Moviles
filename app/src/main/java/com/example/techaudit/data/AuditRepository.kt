package com.example.techaudit.data

import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

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

}