package com.example.yugiohdeckbuilder.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.data.model.response.Card
import com.example.yugiohdeckbuilder.databinding.CardRowItemBinding
import com.example.yugiohdeckbuilder.utils.PLACEHOLDER_GIF

class CardAdapter(
    private val cardList: List<Card>,
    private val details: (Card) -> Unit
) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(
            CardRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(cardList[position], details)
    }

    override fun getItemCount() = cardList.size

}

class CardViewHolder(
    private val binding: CardRowItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(card: Card, details: (Card) -> Unit) {

        Glide.with(binding.ivCardThumbnail)
            .load(card.cardImages[0].imageUrlSmall)
            .placeholder(R.drawable.placeholder)
            .thumbnail(Glide.with(binding.ivCardThumbnail).load(PLACEHOLDER_GIF))
            .dontAnimate()
            .into(binding.ivCardThumbnail)


        binding.root.apply {
            setOnClickListener { details(card) }
            animation = AnimationUtils.loadAnimation(context, R.anim.row_item_anim)
        }
    }
}