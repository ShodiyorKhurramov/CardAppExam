package com.example.cardappexam.database

import com.example.cardappexam.model.Card



import androidx.room.*
import androidx.room.Dao


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Query("SELECT * FROM card")
    fun getCards(): List<Card>

    @Query("DELETE FROM card")
    fun delete()
}