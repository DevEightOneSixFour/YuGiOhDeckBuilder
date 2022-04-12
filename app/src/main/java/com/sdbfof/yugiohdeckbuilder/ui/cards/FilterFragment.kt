package com.sdbfof.yugiohdeckbuilder.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.data.model.states.YUIState
import com.sdbfof.yugiohdeckbuilder.databinding.CustomFilterAlertBinding
import com.sdbfof.yugiohdeckbuilder.databinding.FragmentFilterBinding
import com.sdbfof.yugiohdeckbuilder.presentation.CardViewModel
import com.sdbfof.yugiohdeckbuilder.ui.CustomAlertView
import com.sdbfof.yugiohdeckbuilder.ui.MainActivity
import com.sdbfof.yugiohdeckbuilder.utils.*

class FilterFragment : BaseCardFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = _binding!!
    private lateinit var alertBinding: CustomFilterAlertBinding
    private val args: FilterFragmentArgs by navArgs()
    private var callback: OnBackPressedCallback? = null
    private val viewModel: CardViewModel by lazy { provideCardViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(layoutInflater)
        configureObservers()

        callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                logoutCheck()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback as OnBackPressedCallback)
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

        binding.actvFname.addTextChangedListener {
            binding.pbNameFilter.visibility = View.VISIBLE
            viewModel.fetchCardByName(fName = it.toString().trim())
        }

        binding.btnSearch.setOnClickListener {
            displaySelectedFilters()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.clearPageState()
        clearFilters()
    }

    private fun displaySelectedFilters() {
        var showAlert = true
        val listOfFilters = mutableListOf<String>()
        when (viewModel.currentType.value) {
            CardType.NAME -> {
                if (binding.actvFname.text.toString() == "") {
                    showAlert = false
                    binding.actvFname.error = resources.getString(R.string.please_enter_card_name)
                } else {
                    listOfFilters.add(binding.actvFname.text.toString())
                }
            }
            CardType.MONSTER -> {
                listOfFilters.apply {
                    add(binding.spnMainTypes.selectedItem.toString())
                    if (binding.spnMonsterRaces.selectedItemPosition != 0) {
                        add(binding.spnMonsterRaces.selectedItem.toString())
                    }
                    if (binding.spnLevels.selectedItemPosition != 0) {
                        add(binding.spnLevels.selectedItem.toString())
                    }
                    if (binding.spnAttributes.selectedItemPosition != 0) {
                        add(binding.spnAttributes.selectedItem.toString())
                    }
                }
            }
            CardType.EXTRA -> {
                listOfFilters.apply {
                    add(binding.spnExtraTypes.selectedItem.toString())
                    if (binding.spnMonsterRaces.selectedItemPosition != 0) {
                        add(binding.spnMonsterRaces.selectedItem.toString())
                    }
                    if (binding.spnLevels.selectedItemPosition != 0) {
                        add(binding.spnLevels.selectedItem.toString())
                    }
                    if (binding.spnAttributes.selectedItemPosition != 0) {
                        add(binding.spnAttributes.selectedItem.toString())
                    }
                }
            }
            CardType.SPELL -> {
                listOfFilters.apply {
                    add(SPELL_CARD)
                    if (binding.spnSpellRaces.selectedItemPosition != 0) {
                        add(binding.spnSpellRaces.selectedItem.toString())
                    }
                }
            }
            CardType.TRAP -> {
                listOfFilters.apply {
                    add(TRAP_CARD)
                    if (binding.spnTrapRaces.selectedItemPosition != 0) {
                        add(binding.spnTrapRaces.selectedItem.toString())
                    }
                }
            }
            CardType.NO_TYPE -> {}
            null -> {}
        }
        if (showAlert) {
            buildFilterAlertView(listOfFilters)
        }
    }

    private fun buildFilterAlertView(list: List<String>) {
        CustomAlertView(requireContext()).buildFilterAlertView(list, this)
    }

    fun moveToCardList() {
        this.findNavController().navigate(
            FilterFragmentDirections.actionNavFilterToNavCardList(
                fName = getByCardName(),
                type = getFilterType(),
                race = getFilterRace(),
                level = getFilterText(binding.spnLevels),
                attribute = getFilterText(binding.spnAttributes),
                language = getFilterLanguage(binding.spnLanguages)
            )
        )
    }

    private fun configureObservers() {
        viewModel.currentType.observe(viewLifecycleOwner) { type ->
            when (type) {
                CardType.NAME -> {
                    nameUIUpdate()
                    binding.btnSearch.text = resources.getString(R.string.fetch_named_cards)
                }
                CardType.MONSTER -> {
                    deckUIUpdate(true)
                    binding.btnSearch.text = resources.getString(R.string.fetch_monster_cards)
                }
                CardType.EXTRA -> deckUIUpdate(false)
                CardType.SPELL -> {
                    spellTrapUIUpdate(true)
                    binding.btnSearch.text = resources.getString(R.string.fetch_spell_cards)
                }
                CardType.TRAP -> {
                    spellTrapUIUpdate(false)
                    binding.btnSearch.text = resources.getString(R.string.fetch_trap_cards)
                }
                CardType.NO_TYPE -> {
                    clearFilters()
                    binding.btnSearch.text = resources.getString(R.string.fetch_all_cards)
                }
                null -> {}
            }
        }

        viewModel.cardByNameLiveData.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is YUIState.SuccessList -> {
                    binding.apply {
                        pbNameFilter.visibility = View.GONE
                        actvFname.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                android.R.layout.select_dialog_item,
                                uiState.response.toFName(requireContext())
                            )
                        )
                    }
                }
                is YUIState.Error -> {
                    binding.apply {
                        pbNameFilter.visibility = View.GONE
                        actvFname.error = resources.getString(R.string.cannot_find)
                    }
                }
                else -> {}
            }
        }
    }

    private fun clearFilters() {
        binding.apply {
            rbNoType.isChecked = true
            pbNameFilter.visibility = View.GONE
            tilNameFilter.visibility = View.GONE
            rgMonsterDeckTypes.visibility = View.GONE
            spnExtraTypes.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnMainTypes.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnMonsterRaces.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnAttributes.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnLevels.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnSpellRaces.apply {
                setSelection(0)
                visibility = View.GONE
            }
            spnTrapRaces.apply {
                setSelection(0)
                visibility = View.GONE
            }
        }
    }

    private fun nameUIUpdate() {
        binding.apply {
            actvFname.text.clear()
            tilNameFilter.visibility = View.VISIBLE
            pbNameFilter.visibility = View.GONE
            rgMonsterDeckTypes.visibility = View.GONE
            spnMainTypes.visibility = View.GONE
            spnExtraTypes.visibility = View.GONE
            spnMonsterRaces.visibility = View.GONE
            spnAttributes.visibility = View.GONE
            spnLevels.visibility = View.GONE
            spnSpellRaces.visibility = View.GONE
            spnTrapRaces.visibility = View.GONE
        }
    }

    private fun deckUIUpdate(isMainDeck: Boolean) {
        binding.apply {
            tilNameFilter.visibility = View.GONE
            spnSpellRaces.visibility = View.GONE
            spnTrapRaces.visibility = View.GONE
            pbNameFilter.visibility = View.GONE
            rgMonsterDeckTypes.visibility = View.VISIBLE
            if (isMainDeck) {
                rbMainDeckMonsters.isChecked = true
                spnMainTypes.visibility = View.VISIBLE
                spnExtraTypes.visibility = View.GONE
            } else {
                rbExtraDeckMonsters.isChecked = true
                spnMainTypes.visibility = View.INVISIBLE
                spnExtraTypes.visibility = View.VISIBLE
            }
            spnMonsterRaces.visibility = View.VISIBLE
            spnAttributes.visibility = View.VISIBLE
            spnLevels.visibility = View.VISIBLE
        }
    }

    private fun spellTrapUIUpdate(isSpell: Boolean) {
        binding.apply {
            pbNameFilter.visibility = View.GONE
            tilNameFilter.visibility = View.GONE
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
            binding.rbName.id -> viewModel.updateSelectedType(CardType.NAME)
            binding.rbMonster.id -> viewModel.updateSelectedType(CardType.MONSTER)
            binding.rbMainDeckMonsters.id -> {
                deckUIUpdate(true)
            }
            binding.rbExtraDeckMonsters.id -> {
                viewModel.updateSelectedType(CardType.EXTRA)
                deckUIUpdate(false)
            }
            binding.rbSpell.id -> viewModel.updateSelectedType(CardType.SPELL)
            binding.rbTrap.id -> viewModel.updateSelectedType(CardType.TRAP)
            else -> viewModel.updateSelectedType(CardType.NO_TYPE)
        }
    }

    private fun getByCardName(): String? {
        return if (binding.rgTypes.checkedRadioButtonId == binding.rbName.id) {
            binding.actvFname.text.toString().trim()
        } else {
            null
        }
    }

    private fun getFilterType(): String? {
        return when (binding.rgTypes.checkedRadioButtonId) {
            binding.rbMonster.id -> {
                if (binding.rbMainDeckMonsters.isChecked) {
                    binding.spnMainTypes.selectedItem.toString()
                } else {
                    binding.spnExtraTypes.selectedItem.toString()
                }
            }
            binding.rbSpell.id -> {
                SPELL_CARD
            }
            binding.rbTrap.id -> {
                TRAP_CARD
            }
            else -> null
        }
    }

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

    private fun logoutCheck() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback?.remove()
        _binding = null
    }
}