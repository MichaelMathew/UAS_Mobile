package com.michael.urgalon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Galon(
    val id_galon: Int,
    val nama_galon: String,
    val harga_galon: Int,
    val image_galon: String,
) : Parcelable
