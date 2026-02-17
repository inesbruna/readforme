package com.example.readforme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TextEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun textDao(): TextDao
}