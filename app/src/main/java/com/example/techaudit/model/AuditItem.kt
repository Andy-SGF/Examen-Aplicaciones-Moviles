package com.example.techaudit.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize

enum class AuditStatus{
    PENDIENTE,
    OPERATIVO,
    DANIADO,
    NO_ENCONTRADO
}
@Entity(tableName = "equipos",
    foreignKeys = [
        ForeignKey(
            entity = Laboratorio::class,
            parentColumns = ["id"],
            childColumns = ["laboratorioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

@Parcelize
data class AuditItem(
    @PrimaryKey
    val id: String, //UUID o Codigo de barras

    val nombre:String,
    val ubicacion:String,
    val fechaRegistro:String,
    val laboratorioId:Int,
    var estado: AuditStatus = AuditStatus.PENDIENTE,
    var notas: String ="",
    var fotoUri:String?=null

):Parcelable