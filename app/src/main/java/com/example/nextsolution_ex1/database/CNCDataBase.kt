package com.example.nextsolution_ex1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CNCObject::class], version = 3)
abstract class CNCDataBase : RoomDatabase() {
    abstract fun cncDao(): CNCDao

    companion object {
        private var INSTANCE: CNCDataBase? = null
        private val DB_NAME = "favourite.db"

        fun getDatabase(context: Context): CNCDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CNCDataBase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}