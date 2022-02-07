package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yugiohdeckbuilder.data.model.response.CardSet
import com.example.yugiohdeckbuilder.databinding.CardsetBottomSheetBinding
import com.example.yugiohdeckbuilder.ui.adapter.CardSetAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CardSetBottomSheet: BottomSheetDialogFragment() {
    lateinit var binding: CardsetBottomSheetBinding
    private val args : CardSetBottomSheetArgs by navArgs()
    lateinit var cardSetArray: Array<CardSet>
    companion object {
        const val KEY = "KEY"
        fun newInstance(cardSets: Array<CardSet>) : CardSetBottomSheet {
            val bottomSheet = CardSetBottomSheet()
            val bundle = Bundle()
            bundle.putParcelableArray(KEY, cardSets)

            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CardsetBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCardSetList.apply {
            adapter = CardSetAdapter(args.cardSetList)
            layoutManager = LinearLayoutManager(context)
        }
    }
}