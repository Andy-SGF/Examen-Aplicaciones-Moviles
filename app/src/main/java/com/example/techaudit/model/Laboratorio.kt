package com.example.techaudit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "laboratorios")

@Parcelize
data class Laboratorio(

    @PrimaryKey
    val id:String,

    val nombre:String,
    val edificio:String
): Parcelable