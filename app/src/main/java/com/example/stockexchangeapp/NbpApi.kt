package com.example.stockexchangeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NbpApi {
    @GET("/exchangerates/tables/A")
    fun getCurrencies(): Call<List<Currency>>

}