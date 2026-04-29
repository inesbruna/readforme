package com.example.readforme.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configurations")
data class ConfigEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val speed: Float,
    val language: String
)
