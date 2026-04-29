package com.example.readforme.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ConfigDao {
    // INSERT
    @Insert
    suspend fun insert(configEntity: ConfigEntity)

    // DELETE
    @Query("DELETE FROM configurations WHERE id = 1")
    suspend fun delete()

    // SEARCH
    @Query("SELECT * FROM configurations WHERE id = 1")
    suspend fun getConfig(): ConfigEntity?

    // UPDATE SPEED
    @Query("UPDATE configurations SET speed = :speed WHERE id = 1")
    suspend fun updateSpeed(speed: Float)

    // UPDATE LANGUAGE
    @Query("UPDATE configurations SET language = :language WHERE id = 1")
    suspend fun updateLanguage(language: String)
}