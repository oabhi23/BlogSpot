package com.example.abhi.blogspot.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Comment (
    val author: User? = null,
    val content: String = "",
    val uid: String = ""
) : Parcelable