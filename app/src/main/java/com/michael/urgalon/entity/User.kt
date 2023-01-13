package com.michael.urgalon.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String? = null,
    val name: String? = null,
    val point: Int? = null,
) {

}
