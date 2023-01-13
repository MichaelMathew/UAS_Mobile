package com.michael.urgalon.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Depot(
    val id_depot: Int? = null,
    val nama_depot: String? = null,
    val alamat_depot: String? = null,
    val image_depot: String? = null,
    val jarak_depot: Double? = null,
) : Parcelable {

}
