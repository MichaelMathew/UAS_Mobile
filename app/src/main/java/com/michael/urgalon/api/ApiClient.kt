package com.michael.urgalon.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getApiClient(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-uas.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}