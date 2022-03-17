package com.example.cardappexam.network


import com.example.cardappexam.model.Card
import retrofit2.Call
import retrofit2.http.*

interface CardService {

    @GET("cards")
    fun getAllCrads(): Call<List<Card>>


    @POST("cards")
    fun createCard(@Body card: Card): Call<Card>



}

