package com.example.gallery

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MyPicItem(val imgId: Int, var desc: String, var rating: Float) : Parcelable