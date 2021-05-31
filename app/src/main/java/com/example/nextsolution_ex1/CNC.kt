package com.example.nextsolution_ex1

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CNC(var index: Int, var title: String, var url: String) : Parcelable