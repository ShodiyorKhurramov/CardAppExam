package com.example.cardappexam.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardappexam.R
import com.example.cardappexam.model.Card


class CardAdapter : RecyclerView.Adapter<CardAdapter.CardVH>() {

    private val cards: ArrayList<Card> = ArrayList()

    inner class CardVH(private val view: View) : RecyclerView.ViewHolder(view) {

        private val tv_cardnumber: TextView = view.findViewById(R.id.tv_cardnumber)
        private val tv_name: TextView = view.findViewById(R.id.tv_name)
        private val tv_date: TextView = view.findViewById(R.id.tv_date)


        fun bind(position: Int) {
            tv_cardnumber.text = cards[position].cardnumber
            tv_name.text = cards[position].cardname
            tv_date.text = cards[position].date




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardVH = CardVH(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_card, parent, false
        )
    )

    override fun onBindViewHolder(holder: CardVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cards.size

    fun submitData(cards: List<Card>) {
        this.cards.addAll(cards)
        notifyDataSetChanged()
    }

    fun addCard(card: Card){
        this.cards.add(card)
        notifyDataSetChanged()
    }

    val String.creditCardFormatted: String
        get() {
            val preparedString = replace(" ", "").trim()
            val result = StringBuilder()
            for (i in preparedString.indices) {
                if (i % 4 == 0 && i != 0) {
                    result.append(" ")
                }
                result.append(preparedString[i])
            }
            return result.toString()
        }
}