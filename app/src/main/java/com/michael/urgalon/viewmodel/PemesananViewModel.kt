package com.michael.urgalon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.urgalon.entity.Metodebayar

class PemesananViewModel : ViewModel() {
    var selectedMetodebayar: String? = null

    private val _indexSpinnerPemb = MutableLiveData<Int>()
    val indexSpinnerPemb: LiveData<Int> = _indexSpinnerPemb

    init {
        _indexSpinnerPemb.value = 0
    }

    fun changeSpinner(i: Int) {
        _indexSpinnerPemb.value = i
    }
}