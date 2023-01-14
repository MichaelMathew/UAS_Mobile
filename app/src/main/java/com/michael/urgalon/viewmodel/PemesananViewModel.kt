package com.michael.urgalon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.urgalon.entity.Metodebayar
import com.michael.urgalon.entity.Voucher

class PemesananViewModel : ViewModel() {
    var selectedMetodebayar: String? = null
    var selectedVoucher: Voucher? = null

    private val _indexSpinnerPemb = MutableLiveData<Int>()
    val indexSpinnerPemb: LiveData<Int> = _indexSpinnerPemb

    init {
        _indexSpinnerPemb.value = 0
    }

    fun changeSpinner(i: Int) {
        _indexSpinnerPemb.value = i
    }
}