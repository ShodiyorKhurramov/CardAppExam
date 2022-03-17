package com.example.cardappexam.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cardappexam.model.Card


@Database(entities = [Card::class], version = 4)
abstract class CardDB : RoomDatabase() {

    abstract fun cardDao(): Dao

    companion object {

        private var instance: CardDB? = null

        @Synchronized
        fun getInstance(context: Context): CardDB {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CardDB::class.java, "card.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}