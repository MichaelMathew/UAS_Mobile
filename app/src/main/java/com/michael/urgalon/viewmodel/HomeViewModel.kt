package com.michael.urgalon.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Galon
import com.michael.urgalon.entity.User
import com.michael.urgalon.entity.Voucher
import com.michael.urgalon.fragment.ProfileFragment

class HomeViewModel : ViewModel() {
    var selectedDepot: Depot? = null
    var typeBeli: Int? = null
    var selectedGalon: MutableMap<Galon, Int> = mutableMapOf()
    var amountIsiUlang: Int = 0
    var selectedVoucher: Voucher? = null

    private val _loggedUser = MutableLiveData<User>()
    val loggedUser: LiveData<User> = _loggedUser

    private val _isFabShow = MutableLiveData<Boolean>()
    val isFabShow: LiveData<Boolean> = _isFabShow

    fun checkFab() {
        _isFabShow.value = selectedDepot != null && (selectedGalon.isNotEmpty() || amountIsiUlang > 0)
    }

    init {
        getLoggedUser()
    }

    private fun getLoggedUser() {
        val database = Firebase.database("https://urgalon-125ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val reference = database.getReference(ProfileFragment.USER_REF)
        Firebase.auth.currentUser?.uid?.let {
            reference.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _loggedUser.value = snapshot.getValue<User>() as User
                }

                override fun onCancelled(error: DatabaseError) {
                    //
                }
            })
        }
    }
}