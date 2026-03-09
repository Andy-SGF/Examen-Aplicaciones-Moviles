package com.example.techaudit.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.techaudit.model.AuditItem
import com.example.techaudit.model.Laboratorio
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditDao {

    //==============================
    // EQUIPOS
    //==============================

    //Traer todos los equipos ordenados por fecha
    @Query("SELECT * FROM equipos ORDER BY fechaRegistro DESC")
    fun getAllItems() : Flow<List<AuditItem>>

    @Query("SELECT * FROM equipos")
    suspend fun getEquiposList(): List<AuditItem>

    //Buscar uno solo por ID
    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getById(id: String) : AuditItem

    //Insertar un nuevo equipo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AuditItem)

    //Actualizar un equipo
    @Update
    suspend fun update(item: AuditItem)

    //Eliminar un equipo
    @Delete
    suspend fun delete(Item: AuditItem)

    //==============================
    // LABORATORIOS
    //==============================

    //Traer todos los laboratorios
    @Query("SELECT * FROM laboratorios")
    fun getLaboratorios() :Flow<List<Laboratorio>>

    //Insertar laboratorio
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaboratorio(lab: Laboratorio)

    //Equipos por laboratorio
    @Query("SELECT * FROM equipos WHERE laboratorioId = :labId")
    fun getEquiposByLaboratorio(labId: Int): Flow<List<AuditItem>>

    @Query("SELECT * FROM laboratorios")
    suspend fun getLaboratoriosList(): List<Laboratorio>

}