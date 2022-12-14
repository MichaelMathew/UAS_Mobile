package com.michael.urgalon.entity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
class Metodebayar(
    private val name: String
) : Parcelable {
    override fun toString(): String {
        return "$name"
    }
}