package com.sdbfof.yugiohdeckbuilder.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.sdbfof.yugiohdeckbuilder.data.model.states.YUIState
import com.sdbfof.yugiohdeckbuilder.data.model.response.Card
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentCardListBinding
import com.sdbfof.yugiohdeckbuilder.ui.adapter.CardAdapter
import com.sdbfof.yugiohdeckbuilder.utils.PAGE_SIZE

class CardListFragment : BaseCardFragment() {

    private var _binding: FragmentCardListBinding? = null
    private val binding: FragmentCardListBinding
        get() = _binding!!
    private val viewModel by lazy { provideCardViewModel() }
    private val args: CardListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(layoutInflater)
        configureObservers()
        fetchCardData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivNextPage.setOnClickListener {
                viewModel.updateOffset(PAGE_SIZE)
            }
            ivPreviousPage.setOnClickListener {
                viewModel.updateOffset(-PAGE_SIZE)
            }
        }
    }

    private fun configureObservers() {
        viewModel.cardListLiveData.observe(viewLifecycleOwner) {
            updateYUI(it)
        }
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
                    checkCurrentPage(state.response.data!!)
                }
            }
            else -> {}
        }
    }

    private fun checkCurrentPage(cardList: List<Card>) {
        when {
            viewModel.getPageState().offset == 0 && PAGE_SIZE != cardList.size -> {
                binding.apply {
                    clPagingLayout.visibility = View.GONE
                }
            }
            viewModel.getPageState().offset == 0 -> {
                binding.apply {
                    clPagingLayout.visibility = View.VISIBLE
                    ivPreviousPage.visibility = View.GONE
                    ivNextPage.visibility = View.VISIBLE
                }
            }
            PAGE_SIZE != cardList.size -> {
                binding.apply {
                    clPagingLayout.visibility = View.VISIBLE
                    ivPreviousPage.visibility = View.VISIBLE
                    ivNextPage.visibility = View.GONE
                }
            }
            viewModel.getPageState().offset!! > 0 -> {
                binding.apply {
                    clPagingLayout.visibility = View.VISIBLE
                    ivPreviousPage.visibility = View.VISIBLE
                    ivNextPage.visibility = View.VISIBLE
                }
            }
        }
    }

    // TODO this function is needed for filter fragment, maybe place in MainActivity
    private fun openCardDetail(card: Card, image: ImageView) {
        val extras = FragmentNavigatorExtras(image to card.cardImages[0].imageUrl)

        val action = CardListFragmentDirections.actionNavCardListToNavCardDetail(card = card)

        with(findNavController()) {
            currentDestination?.getAction(action.actionId) ?.let {
                navigate(action, extras)
            }
        }
    }

    private fun fetchCardData() {
        viewModel.updatePageState(
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
