package com.michael.urgalon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.urgalon.api.ApiClient
import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Galon
import com.michael.urgalon.entity.Voucher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _galons = MutableLiveData<ArrayList<Galon>>()
    val galons: LiveData<ArrayList<Galon>> = _galons

    private val _depots = MutableLiveData<ArrayList<Depot>>()
    val depots: LiveData<ArrayList<Depot>> = _depots

    private val _vouchers = MutableLiveData<ArrayList<Voucher>>()
    val vouchers: LiveData<ArrayList<Voucher>> = _vouchers

    init {
        loadGalons()
        loadDepots()
        loadVouchers()
    }

    private fun loadGalons() {
        val api = ApiClient.getApiClient()
        api.getGalons().enqueue(object : Callback<ArrayList<Galon>> {
            override fun onResponse(
                call: Call<ArrayList<Galon>>,
                response: Response<ArrayList<Galon>>
            ) {
                _galons.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Galon>>, t: Throwable) {
                Log.d("GALON", t.message.toString())
            }
        })
    }

    private fun loadDepots() {
        val api = ApiClient.getApiClient()
        api.getDepots().enqueue(object : Callback<ArrayList<Depot>> {
            override fun onResponse(
                call: Call<ArrayList<Depot>>,
                response: Response<ArrayList<Depot>>
            ) {
                _depots.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Depot>>, t: Throwable) {
                Log.d("DEPOT", t.message.toString())
            }
        })
    }

    private fun loadVouchers() {
        val api = ApiClient.getApiClient()
        api.getVouchers().enqueue(object : Callback<ArrayList<Voucher>> {
            override fun onResponse(
                call: Call<ArrayList<Voucher>>,
                response: Response<ArrayList<Voucher>>
            ) {
                _vouchers.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<Voucher>>, t: Throwable) {
                Log.d("GALON", t.message.toString())
            }
        })
    }
}