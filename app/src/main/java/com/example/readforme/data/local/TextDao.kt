package com.example.readforme.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TextDao {
    @Insert
    suspend fun insert(text: TextEntity)

    @Query("DELETE FROM texts")
    suspend fun deleteAll()

    @Query("SELECT * FROM texts ORDER BY timestamp DESC")
    fun getAllTexts(): Flow<List<TextEntity>>

    @Query("SELECT * FROM texts ORDER BY id DESC LIMIT 1")
    fun getLastText(): Flow<TextEntity>
}