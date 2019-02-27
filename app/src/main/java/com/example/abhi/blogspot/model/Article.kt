package com.example.abhi.blogspot.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Article (
    val author: User? = null,
    val title: String = "",
    val picture: String = "",
    val uid: String = "",
    val content: String = ""
) : Parcelable