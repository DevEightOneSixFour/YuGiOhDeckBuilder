package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yugiohdeckbuilder.R
import com.example.yugiohdeckbuilder.databinding.FragmentFilterBinding
import com.example.yugiohdeckbuilder.di.DI
import com.example.yugiohdeckbuilder.presentation.CardViewModel
import com.example.yugiohdeckbuilder.utils.*

class FilterFragment: Fragment() {

    private lateinit var binding : FragmentFilterBinding
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

        binding.btnSearch.setOnClickListener {
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
    }

    private fun configureObservers() {
        viewModel.currentType.observe(viewLifecycleOwner, { type ->
            when (type) {
                CardType.MONSTER -> {
                    Toast.makeText(context, CardType.MONSTER.name, Toast.LENGTH_SHORT).show()
                }
                CardType.SPELL -> {
                    Toast.makeText(context, CardType.SPELL.name, Toast.LENGTH_SHORT).show()
                }
                CardType.TRAP -> {
                    Toast.makeText(context, CardType.TRAP.name, Toast.LENGTH_SHORT).show()
                }
                CardType.NO_TYPE -> {
                    Toast.makeText(context, CardType.NO_TYPE.name, Toast.LENGTH_SHORT).show()
                }
            }
        })
        Log.d("*****", "FilterFragmentVM: $viewModel")
    }

    private fun updateFilters(btnId: Int) {
        when(btnId) {
            binding.rbMonster.id -> viewModel.updateSelectedType(CardType.MONSTER)
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
            CardType.SPELL -> {
                resources.getString(R.string.spell_card)
            }
            CardType.TRAP -> {
                resources.getString(R.string.trap_card)
            }
            else -> null
        }
    }
    private fun getFilterRace(): String? {
        return when (viewModel.currentType.value) {
            CardType.MONSTER -> {
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
        return if (viewModel.currentType.value != CardType.MONSTER) {
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