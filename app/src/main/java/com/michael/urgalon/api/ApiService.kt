package com.michael.urgalon.api

import com.michael.urgalon.entity.Depot
import com.michael.urgalon.entity.Galon
import com.michael.urgalon.entity.Voucher
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("fetch-all-galon.php")
    fun getGalons(
    ): Call<ArrayList<Galon>>

    @GET("fetch-all-depot.php")
    fun getDepots(
    ): Call<ArrayList<Depot>>

    @GET("fetch-all-voucher.php")
    fun getVouchers(
    ): Call<ArrayList<Voucher>>

    @GET("fetch-galon.php")
    fun getGalon(
        @Query("id") id: Int
    ): Call<Galon>

    @GET("fetch-depot.php")
    fun getDepot(
        @Query("id") id: Int
    ): Call<Depot>

    @GET("fetch-voucher.php")
    fun getVoucher(
        @Query("id") id: Int
    ): Call<Voucher>
}