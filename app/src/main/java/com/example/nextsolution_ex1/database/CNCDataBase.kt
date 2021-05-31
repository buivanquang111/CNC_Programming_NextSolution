package com.example.nextsolution_ex1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CNCObject::class],version = 1)
abstract class CNCDataBase : RoomDatabase(){

    private final val DATABASE_NAME: String = "favourite.db"
    private lateinit var instance: CNCDataBase
    fun getInstance(context: Context): CNCDataBase{
        if (instance == null){
            instance = Room.databaseBuilder(context.applicationContext, CNCDataBase::class.java,DATABASE_NAME).allowMainThreadQueries().build()
        }
        return instance
    }
    abstract fun cncDao(): CNCDao

}