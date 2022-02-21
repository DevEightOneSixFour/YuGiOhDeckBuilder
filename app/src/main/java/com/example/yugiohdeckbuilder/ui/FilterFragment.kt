package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.databinding.FragmentFilterBinding
import com.example.yugiohdeckbuilder.presentation.CardViewModel
import com.example.yugiohdeckbuilder.utils.*

class FilterFragment : Fragment() {

    private lateinit var binding: FragmentFilterBinding
    private lateinit var viewModel: CardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[CardViewModel::class.java]

        configureObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rgTypes.setOnCheckedChangeListener { radioGroup, _ ->
            updateFilters(radioGroup.checkedRadioButtonId)
        }
        binding.rgMonsterDeckTypes.setOnCheckedChangeListener { radioGroup, _ ->
            updateFilters(radioGroup.checkedRadioButtonId)
        }

        binding.btnSearch.setOnClickListener {
            moveToCardList()
//            displaySelectedFilters()
        }
    }

    override fun onResume() {
        super.onResume()
        clearFilters()
    }

    /*
     TODO should display selected filters
        for the respective card type
        in an alert dialog, before
        prompting the user to search
        with the selected filters

                You Selected
        Monster, Effect Monster, 4, Dark
         [go back]  [search with these filters]
     */

    private fun displaySelectedFilters() {
        val listOfFilters = mutableListOf<String>()
        when (viewModel.currentType.value) {
            CardType.MONSTER -> {
                listOfFilters.run {
                    add(binding.spnMainTypes.selectedItem.toString())
                }
            }
            CardType.EXTRA -> {

            }
            CardType.SPELL -> {

            }
            CardType.TRAP -> {

            }
        }

    }

    private fun moveToCardList() {
        this.findNavController().navigate(
            FilterFragmentDirections.actionNavFilterToNavCardList(
                name = null, //todo
                type = getFilterType(),
                race = getFilterRace(),
                level = getFilterText(binding.spnLevels),
                attribute = getFilterText(binding.spnAttributes),
                language = getFilterLanguage(binding.spnLanguages)
            )
        )
    }

    private fun configureObservers() {
        viewModel.currentType.observe(viewLifecycleOwner, { type ->
            when (type) {
                CardType.MONSTER -> deckFilterUpdate(true)
                CardType.EXTRA -> deckFilterUpdate(false)
                CardType.SPELL -> spellTrapFilterUpdate(true)
                CardType.TRAP -> spellTrapFilterUpdate(false)
                CardType.NO_TYPE -> clearFilters()
            }
        })
    }

    /*
     TODO clear filters on return to this page
        and reselect no_filter button
     */

    private fun clearFilters() {
        binding.run {
            spnSpellRaces.visibility = View.GONE
            spnTrapRaces.visibility = View.GONE
            rgMonsterDeckTypes.visibility = View.GONE
            spnExtraTypes.visibility = View.GONE
            spnMainTypes.visibility = View.GONE
            spnMonsterRaces.visibility = View.GONE
            spnAttributes.visibility = View.GONE
            spnLevels.visibility = View.GONE
        }
    }

    private fun deckFilterUpdate(isMainDeck: Boolean) {
        //todo update UI spinners and search button here
        binding.run {
            spnSpellRaces.visibility = View.GONE
            spnTrapRaces.visibility = View.GONE
            rgMonsterDeckTypes.visibility = View.VISIBLE
            if (isMainDeck) {
                spnMainTypes.visibility = View.VISIBLE
                spnExtraTypes.visibility = View.GONE
            } else {
                spnMainTypes.visibility = View.INVISIBLE
                spnExtraTypes.visibility = View.VISIBLE
            }
            spnMonsterRaces.visibility = View.VISIBLE
            spnAttributes.visibility = View.VISIBLE
            spnLevels.visibility = View.VISIBLE
        }
    }

    private fun spellTrapFilterUpdate(isSpell: Boolean) {
        binding.run {
            rgMonsterDeckTypes.visibility = View.GONE
            spnMainTypes.visibility = View.GONE
            spnExtraTypes.visibility = View.GONE
            spnMonsterRaces.visibility = View.GONE
            spnAttributes.visibility = View.GONE
            spnLevels.visibility = View.GONE

            if (isSpell) {
                spnSpellRaces.visibility = View.VISIBLE
                spnTrapRaces.visibility = View.GONE
            } else {
                spnSpellRaces.visibility = View.GONE
                spnTrapRaces.visibility = View.VISIBLE
            }
        }
    }

    private fun updateFilters(btnId: Int) {
        when (btnId) {
            binding.rbMonster.id, binding.rbMainDeckMonsters.id ->
                viewModel.updateSelectedType(CardType.MONSTER)
            binding.rbExtraDeckMonsters.id -> viewModel.updateSelectedType(CardType.EXTRA)
            binding.rbSpell.id -> viewModel.updateSelectedType(CardType.SPELL)
            binding.rbTrap.id -> viewModel.updateSelectedType(CardType.TRAP)
            else -> viewModel.updateSelectedType(CardType.NO_TYPE)
        }
    }

    private fun getFilterType(): String? {
        return when (viewModel.currentType.value) {
            CardType.MONSTER -> {
                binding.spnMainTypes.selectedItem.toString()
            }
            CardType.EXTRA -> {
                binding.spnExtraTypes.selectedItem.toString()
            }
            CardType.SPELL -> {
                resources.getString(R.string.spell_card)
            }
            CardType.TRAP -> {
                resources.getString(R.string.trap_card)
            }
            else -> null
        }
    }

    // todo when no_type is selected
    private fun getFilterRace(): String? {
        return when (viewModel.currentType.value) {
            CardType.MONSTER, CardType.EXTRA -> {
                if (binding.spnMonsterRaces.selectedItemPosition == 0) {
                    null
                } else {
                    binding.spnMonsterRaces.selectedItem.toString()
                }
            }
            CardType.SPELL -> {
                if (binding.spnSpellRaces.selectedItemPosition == 0) {
                    null
                } else {
                    binding.spnSpellRaces.selectedItem.toString()
                }
            }
            CardType.TRAP -> {
                if (binding.spnTrapRaces.selectedItemPosition == 0) {
                    null
                } else {
                    binding.spnTrapRaces.selectedItem.toString()
                }
            }
            else -> null
        }
    }

    private fun getFilterText(spinner: Spinner): String? {
        return if (viewModel.currentType.value == CardType.SPELL ||
            viewModel.currentType.value == CardType.TRAP ||
            viewModel.currentType.value == CardType.NO_TYPE
        ) {
            null
        } else {
            when (spinner.selectedItemPosition) {
                0 -> null
                else -> spinner.selectedItem.toString()
            }
        }
    }

    private fun getFilterLanguage(spinner: Spinner): String? {
        return when (spinner.selectedItem) {
            resources.getStringArray(R.array.languages)[1] -> FRENCH
            resources.getStringArray(R.array.languages)[2] -> GERMAN
            resources.getStringArray(R.array.languages)[3] -> ITALIAN
            resources.getStringArray(R.array.languages)[4] -> PORTUGUESE
            else -> null
        }
    }
}