package com.global.tutorial

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CNC(var index: Int, var title: String, var url: String) : Parcelable