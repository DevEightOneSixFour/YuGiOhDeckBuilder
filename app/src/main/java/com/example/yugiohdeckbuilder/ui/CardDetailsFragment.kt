package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.yugiohdeckbuilder.databinding.FragmentDetailsBinding

class CardDetailsFragment: Fragment() {

    private val binding by lazy { FragmentDetailsBinding.inflate(layoutInflater) }
    private val args : CardDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            Glide.with(ivLargeCard)
                .load(args.card.cardImages[0].imageUrl)
                .into(ivLargeCard)
        }
    }
}