package com.michael.urgalon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Voucher(
    val id_voucher: Int,
    val point_voucher: Int,
    val diskon_voucher: Int,
    val tanggal_voucher: String,
) : Parcelable
