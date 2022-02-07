package com.example.yugiohdeckbuilder.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.data.model.response.CardSet
import com.example.yugiohdeckbuilder.databinding.CardsetRowItemBinding

class CardSetAdapter(private val cardSetList: Array<CardSet>) :
    RecyclerView.Adapter<CardSetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardSetViewHolder(
            CardsetRowItemBinding.inflate(
                LayoutInflater.from(parent.context)
            ), parent.context
        )

    override fun onBindViewHolder(holder: CardSetViewHolder, position: Int) {
        holder.onBind(cardSetList[position])
    }

    override fun getItemCount() = cardSetList.size
}

class CardSetViewHolder(
    private val binding: CardsetRowItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(cardSet: CardSet) {
        binding.apply {
            tvCardSetName.text = cardSet.setName
            tvCardSetRarity.text = context.resources.getString(R.string.card_set_rarity, cardSet.setRarity)
            tvCardSetPrice.text = context.resources.getString(R.string.card_set_price, cardSet.setPrice)
        }
    }
}