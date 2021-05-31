package com.example.nextsolution_ex1.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CNCDao {
    @Insert
    suspend fun insertCNC(cncObject: CNCObject)

    @Delete
    suspend fun delete(cncObject: CNCObject)

    @Query("SELECT * FROM favourite")
    suspend fun getListCNC(): List<CNCObject>
}