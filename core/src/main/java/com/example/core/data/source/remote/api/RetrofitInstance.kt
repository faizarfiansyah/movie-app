package com.example.core.data.source.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: API = retrofit.create(API::class.java)
}