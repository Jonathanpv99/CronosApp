package com.example.cronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cronoapp.model.Cronos

@Database(entities = [Cronos::class], version = 1, exportSchema = false)
abstract class CronosDataBase: RoomDatabase() {
    abstract fun cronosDao(): CronosDatabaseDao
}