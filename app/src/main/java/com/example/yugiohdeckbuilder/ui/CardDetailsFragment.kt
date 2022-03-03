package com.example.yugiohdeckbuilder.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.databinding.CardDetailsListViewBinding
import com.example.yugiohdeckbuilder.databinding.CardsetBottomSheetBinding
import com.example.yugiohdeckbuilder.databinding.FragmentDetailsBinding
import com.example.yugiohdeckbuilder.ui.adapter.CardSetAdapter

class CardDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var bottomSheetBinding: CardsetBottomSheetBinding
    private lateinit var dialog: Dialog
    private val args: CardDetailsFragmentArgs by navArgs()
    private lateinit var priceArray: Array<String>
    private lateinit var miscInfo: Array<String> // todo implement?
    private lateinit var banArray: Array<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createListViews()

        Glide.with(binding.ivLargeCard)
            .load(args.card.cardImages[0].imageUrl)
            .placeholder(ResourcesCompat.getDrawable(resources, R.drawable.back_ground, null))
            .into(binding.ivLargeCard)

        binding.apply {
            btnViewPrices.setOnClickListener {
                handleButtonClick(it as Button)
            }
            btnViewCardSets.setOnClickListener {
                if (args.card.cardSets.isNullOrEmpty()) {
                    showCardDetails(emptyArray(), resources.getString(R.string.card_set_empty))
                } else {
                    handleButtonClick(it as Button)
                }
            }
            btnViewBanList.setOnClickListener {
                if (banArray.isNullOrEmpty()) {
                    showCardDetails(emptyArray(), resources.getString(R.string.ban_never_banned))
                } else {
                    handleButtonClick(it as Button)
                }
            }
        }
    }

    private fun handleButtonClick(btn: Button) {
        when (btn.id) {
            binding.btnViewPrices.id -> {
                showCardDetails(priceArray, btn.text)
            }
            binding.btnViewCardSets.id -> {
                bottomSheetBinding = CardsetBottomSheetBinding.inflate(layoutInflater)
                dialog = Dialog(requireContext())
                dialog.setContentView(bottomSheetBinding.root)
                bottomSheetBinding.rvCardSetList.apply {
                    adapter = CardSetAdapter(args.card.cardSets.toTypedArray())
                    layoutManager = LinearLayoutManager(context)
                }
                dialog.show()
            }
            binding.btnViewBanList.id -> {
                showCardDetails(banArray, btn.text)
            }
        }
    }

    private fun showCardDetails(array: Array<String>?, title: CharSequence) {
        if (array.isNullOrEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .show()
        } else {
            val listView: ListView =
                CardDetailsListViewBinding.inflate(layoutInflater).lvCardDetails
            listView.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, array.orEmpty())
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setView(listView)
                .show()
        }
    }

    private fun createListViews() {
        priceArray = arrayOf(
            resources.getString(
                R.string.price_cardmarket,
                args.card.cardPrices[0].tcgPlayerPrice
            ),
            resources.getString(
                R.string.price_coolstuff,
                args.card.cardPrices[0].coolStuffIncPrice
            ),
            resources.getString(R.string.price_tcgplayer, args.card.cardPrices[0].tcgPlayerPrice),
            resources.getString(R.string.price_ebay, args.card.cardPrices[0].ebayPrice),
            resources.getString(R.string.price_amazon, args.card.cardPrices[0].amazonPrice)
        )

        if (args.card.banListInfo != null) {
            banArray = arrayOf(
                resources.getString(
                    R.string.ban_tcg,
                    args.card.banListInfo?.banTCG ?: resources.getString(
                        R.string.ban_not_banned_here
                    )
                ),
                resources.getString(
                    R.string.ban_ocg,
                    args.card.banListInfo?.banOCG ?: resources.getString(
                        R.string.ban_not_banned_here
                    )
                ),
                resources.getString(
                    R.string.ban_goat,
                    args.card.banListInfo?.banGOAT ?: resources.getString(
                        R.string.ban_not_banned_here
                    )
                )
            )
        } else {
            banArray = emptyArray()
        }
    }
}