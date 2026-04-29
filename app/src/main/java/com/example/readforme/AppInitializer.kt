package com.example.readforme

import com.example.readforme.data.local.ConfigDao
import com.example.readforme.data.local.ConfigEntity
import javax.inject.Inject

class AppInitializer @Inject constructor(
    private val configDao: ConfigDao
) {
    suspend fun init() {
        if (configDao.getConfig() == null){
            configDao.insert(
                ConfigEntity(1, 1.0f, "pt-BR")
            )
        }
    }
}