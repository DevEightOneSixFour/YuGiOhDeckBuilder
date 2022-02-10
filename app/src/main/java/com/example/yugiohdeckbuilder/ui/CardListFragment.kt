package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.yugiohdeckbuilder.data.model.YUIState
import com.example.yugiohdeckbuilder.data.model.response.Card
import com.example.yugiohdeckbuilder.databinding.FragmentCardListBinding
import com.example.yugiohdeckbuilder.di.DI
import com.example.yugiohdeckbuilder.presentation.CardViewModel
import com.example.yugiohdeckbuilder.ui.adapter.CardAdapter

class CardListFragment : Fragment() {

    private lateinit var binding : FragmentCardListBinding
    private lateinit var viewModel : CardViewModel
    private val args: CardListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[CardViewModel::class.java]
        configureObservers()
        fetchCardData()
        return binding.root
    }
    private fun configureObservers() {
        viewModel.cardLiveData.observe(viewLifecycleOwner, {
            updateYUI(it)
        })
    }

    private fun updateYUI(state: YUIState) {
        when (state) {
            is YUIState.Loading -> {
                binding.apply {
                    progressBar.visibility = View.VISIBLE
                    tvErrorText.visibility = View.GONE
                    rvCardList.visibility = View.GONE
                }
            }
            is YUIState.Error -> {
                binding.apply {
                    progressBar.visibility = View.GONE
                    tvErrorText.text = state.errorMsg
                    tvErrorText.visibility = View.VISIBLE
                    rvCardList.visibility = View.GONE
                }
            }
            is YUIState.SuccessList -> {
                binding.apply {
                    progressBar.visibility = View.GONE
                    tvErrorText.visibility = View.GONE
                    rvCardList.adapter =
                        CardAdapter(state.response.data.orEmpty(), ::openCardDetail)
                    rvCardList.layoutManager = GridLayoutManager(context, 3)
                    rvCardList.visibility = View.VISIBLE
                }
            }
        }
    }

    // TODO this function is needed for filter fragment, maybe place in MainActivity
    private fun openCardDetail(card: Card) {
        binding.root.findNavController().navigate(
            CardListFragmentDirections.actionNavCardListToNavCardDetail(
                card = card
            )
        )
    }

    private fun fetchCardData() {
        viewModel.fetchCards(
            name = args.name,
            archetype = args.archetype,
            level = args.level,
            attribute = args.attribute,
            sort = args.sort,
            banList = args.banList,
            cardSet = args.cardSet,
            fName = args.fName,
            type = args.type,
            race = args.race,
            format = args.format,
            linkMarker = args.linkMarker,
            staple = args.staple,
            language = args.language
        )
    }
}
