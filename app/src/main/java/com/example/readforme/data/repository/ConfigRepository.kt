package com.example.readforme.data.repository

import android.util.Log
import com.example.readforme.data.local.AppDatabase
import com.example.readforme.data.local.ConfigDao
import com.example.readforme.data.local.ConfigEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepository @Inject constructor(
    private val configDao: ConfigDao
) {
    suspend fun saveConfig(configEntity: ConfigEntity){
        configDao.insert(configEntity)
    }

    suspend fun deleteConfig(){
        configDao.delete()
    }

    suspend fun updateSpeed(speed: Float){
        Log.d("WARNING", "Chamando DAO")
        return configDao.updateSpeed(speed = speed)
    }

    suspend fun updateLanguage(language: String){
        return configDao.updateLanguage(language = language)
    }

    suspend fun getConfig(): ConfigEntity? {
        return configDao.getConfig()
    }
}