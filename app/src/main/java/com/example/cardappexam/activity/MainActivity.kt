package com.example.cardappexam.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.cardappexam.R
import com.example.cardappexam.adapter.CardAdapter
import com.example.cardappexam.database.CardDB
import com.example.cardappexam.model.Card
import com.example.cardappexam.network.ApiClient
import com.example.cardappexam.network.CardService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity() {

    private lateinit var service: CardService
    private lateinit var ivAddCard: ImageView
    private lateinit var rvCards: RecyclerView
    private lateinit var cardAdapter: CardAdapter
  private lateinit var cardDB: CardDB

    override fun onCreate(savedInstanceState: Bundle?) {

        window.statusBarColor = Color.parseColor("#000000")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = ApiClient.createService(CardService::class.java)
       cardDB = CardDB.getInstance(this)

        initViews()
    }

    private fun initViews() {
        rvCards = findViewById(R.id.rvCards)
        ivAddCard = findViewById(R.id.iv_addCard)
        cardAdapter = CardAdapter()
        getCards()
        refreshAdapter()

        ivAddCard.setOnClickListener {
            addCard()
        }
    }

    val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            val cardToAdd = data?.getSerializableExtra("card")
            Log.d("TAG", "details: $cardToAdd")
            saveCard(cardToAdd as Card)
        }
    }

    private fun saveCard(card: Card) {
        if (isInternetAvailable()) {
            service.createCard(card).enqueue(object : Callback<Card> {
                override fun onResponse(call: Call<Card>, response: Response<Card>) {
                    card.isAvailable = true
                    card.id = null
                    saveToDatabase(card)
                    cardAdapter.addCard(response.body()!!)
                    Toast.makeText(this@MainActivity, "Card saved", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Card>, t: Throwable) {

                }
            })
        } else {
            card.isAvailable = false
            card.id = null
            saveToDatabase(card)
            cardAdapter.addCard(card)
        }
    }

    private fun saveToDatabase(card: Card) {
        cardDB.cardDao().addCard(card)
    }

    private fun addCard() {
        val intent = Intent(this, AddCardActivity::class.java)
        detailLauncher.launch(intent)
    }

    private fun refreshAdapter() {
        rvCards.adapter = cardAdapter
    }

    private fun getCards() {
        if (isInternetAvailable()) {

            val unSavedCards: ArrayList<Card> = ArrayList()
            val savedCards = cardDB.cardDao().getCards()
            savedCards.forEach {
                if (!it.isAvailable) {
                    unSavedCards.add(it)
                }
            }

            Log.d("TAG", "getCards: $unSavedCards")

            unSavedCards.forEach {
                it.isAvailable = true
                cardDB.cardDao().addCard(it)
            }

            unSavedCards.forEach {
                service.createCard(it).request()
            }

            service.getAllCrads().enqueue(object : Callback<List<Card>> {
                override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                    if (response.body() != null) {
                        Log.d("@@@", "onResponse: ${response.body().toString()}")
                        cardAdapter.submitData(response.body()!!)

                    } else {
                        Log.e("@@@" ,"onResponse: null")
                    }


                }

                override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                    Log.e("@@@", "onFailure: ${t.localizedMessage}")
                }
            })
        } else {


            cardAdapter.submitData(cardDB.cardDao().getCards())
        }
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}