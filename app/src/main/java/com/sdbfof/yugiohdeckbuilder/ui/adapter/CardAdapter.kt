package com.sdbfof.yugiohdeckbuilder.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.databinding.CardRowItemBinding
import com.sdbfof.yugiohdeckbuilder.utils.PLACEHOLDER_GIF
import kotlin.reflect.KFunction2

class CardAdapter(
    private val cardList: List<Card>,
    private val details: KFunction2<Card, ImageView, Unit>
) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CardViewHolder(
            CardRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(cardList[position], details)
    }

    override fun getItemCount() = cardList.size

}

class CardViewHolder(private val binding: CardRowItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun onBind(card: Card, details: KFunction2<Card, ImageView, Unit>) {
        binding.tvCardName.text = card.name
        binding.ivCardThumbnail.transitionName = card.cardImages[0].imageUrl

        Glide.with(binding.ivCardThumbnail)
            .load(card.cardImages[0].imageUrl)
            .placeholder(R.drawable.placeholder)
            .thumbnail(Glide.with(binding.ivCardThumbnail).load(PLACEHOLDER_GIF))
            .into(binding.ivCardThumbnail)


        binding.root.apply {
            setOnClickListener { details(card,binding.ivCardThumbnail) }
            animation = AnimationUtils.loadAnimation(context, R.anim.row_item_anim)
        }
    }
}