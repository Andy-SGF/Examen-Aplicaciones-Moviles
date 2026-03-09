package com.example.techaudit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.techaudit.TechAuditApp
import com.example.techaudit.data.AuditRepository
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.launch

class LaboratorioViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuditRepository

    val allLaboratorios: LiveData<List<Laboratorio>>

    init{
        val dao = (application as TechAuditApp).database.auditDao()
        repository = AuditRepository(dao)

        allLaboratorios = repository.allLaboratorios.asLiveData()
    }

    fun insert (lab: Laboratorio) = viewModelScope.launch{
        repository.insertLaboratorio(lab)
    }

    fun sincronizar() = viewModelScope.launch{
        repository.sincronizarDatos()
    }

}