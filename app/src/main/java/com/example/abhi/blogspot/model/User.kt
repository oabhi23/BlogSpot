package com.example.abhi.blogspot.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class User (
    val name: String = "",
    val email: String = "",
    val uid: String = ""
) : Parcelable