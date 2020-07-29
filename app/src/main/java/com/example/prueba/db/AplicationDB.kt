package com.example.prueba.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prueba.models.*

@Database(
    entities = [
        Apollo::class,
        KeyWords::class,
        Favorite::class
            ],
    version = 20,
    exportSchema = false
)
abstract class AplicationDB : RoomDatabase() {
    abstract fun dataBaseDao(): DataBaseDao
}