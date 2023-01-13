package com.michael.urgalon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HistoryPemesanan (
    var depot: Depot? = null,
    var date: String? = null,
    var totalharga: Int? = null,
    var pesanan: String? = null,
    var point: String? = null,
    var lokasi: String? = null,
    var metodebayar: String? = null,
    var diskon: Int? = null,
) : Parcelable {

}
