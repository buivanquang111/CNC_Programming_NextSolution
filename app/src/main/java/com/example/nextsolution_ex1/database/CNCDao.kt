package com.example.nextsolution_ex1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CNCDao {
    @Insert
    fun insertCNC(cncObject: CNCObject)

    @Query("SELECT * FROM favourite")
    fun getListCNC(): List<CNCObject>
}