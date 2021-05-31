package com.example.nextsolution_ex1.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class CNCObject(
        @PrimaryKey(autoGenerate = true) var id: Int=0,
        var index: Int,
        var title: String,
        var url: String)
