package com.example.readforme.data.repository

import com.example.readforme.data.local.TextDao
import com.example.readforme.data.local.TextEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextRepository @Inject constructor(
    private val textDao: TextDao
) {
    suspend fun saveText(text: String){
        textDao.insert(TextEntity(content = text))
    }

    fun getAllTexts(): Flow<List<TextEntity>> {
        return textDao.getAllTexts()
    }
}