package com.example.readforme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TextEntity::class, ConfigEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textDao(): TextDao
    abstract fun configDao(): ConfigDao
}