package com.example.cardappexam.activity

import com.example.cardappexam.R
import com.example.cardappexam.model.Card


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.google.android.material.button.MaterialButton

class AddCardActivity : AppCompatActivity() {

    private lateinit var tv_cardnumber: TextView
    private lateinit var tv_name: TextView
    private lateinit var tv_date: TextView
    private lateinit var et_cardnumber: EditText
    private lateinit var et_year: EditText
    private lateinit var et_moth: EditText
    private lateinit var et_cvv: EditText
    private lateinit var et_name: EditText
    private lateinit var ic_close : ImageView

    private lateinit var bt_add: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {

        window.statusBarColor = Color.parseColor("#393939")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        initViews()
    }

    private fun initViews() {
        ic_close=findViewById(R.id.iv_close)
        tv_cardnumber = findViewById(R.id.tv_cardnumber)
        tv_name = findViewById(R.id.tv_name)
        tv_date = findViewById(R.id.tv_date)

        et_cardnumber = findViewById(R.id.et_cardnumber)
        et_year = findViewById(R.id.et_year)
        et_moth = findViewById(R.id.et_moth)
        et_cvv = findViewById(R.id.et_cvv)
        et_name = findViewById(R.id.et_name)

        bt_add = findViewById(R.id.bt_add)

        bt_add.setOnClickListener {
            if (allFieldsAdded()) {
                addCard(
                    Card(
                        cardnumber = et_cardnumber.text.toString(),
                        cardname = et_name.text.toString(),
                        date = "${et_year.text}/${et_moth.text}",
                        cvv = et_cvv.text.toString(),
                        isAvailable = false
                    )
                )
            }
        }

        ic_close.setOnClickListener{
            activityClose()
        }

    }

    private fun allFieldsAdded(): Boolean {
        return et_cardnumber.text.isNotBlank() && et_year.text.isNotBlank() && et_moth.text.isNotBlank() && et_cvv.text.isNotBlank() && et_name.text.isNotBlank()
    }

    private fun addCard(card: Card) {
        Log.d("TAG", "addCard: $card")
        val intent = Intent()
        intent.putExtra("card", card)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun activityClose(){
        finish()
    }
}