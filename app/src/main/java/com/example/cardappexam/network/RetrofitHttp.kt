package com.example.cardappexam.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object ApiClient {
    var BASE_URl = "https://623303066de3467dbac5d129.mockapi.io/"

    private val retrofit = getRetrofit()


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URl)
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}


