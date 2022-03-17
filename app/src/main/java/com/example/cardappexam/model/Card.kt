package com.example.cardappexam.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "card")
data class Card(

    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    val date: String,
    val cvv: String,
    val cardnumber: String,
    val cardname: String,
    var isAvailable: Boolean,


    ):Serializable