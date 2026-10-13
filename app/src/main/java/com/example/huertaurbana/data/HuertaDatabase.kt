package com.example.huertaurbana.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SowingEntity::class, WateringEntity::class], version = 1, exportSchema = false)
abstract class HuertaDatabase : RoomDatabase() {
    abstract fun dao(): HuertaDao

    companion object {
        @Volatile private var instance: HuertaDatabase? = null

        fun get(context: Context): HuertaDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, HuertaDatabase::class.java, "huerta.db")
                    .build().also { instance = it }
            }
    }
}
