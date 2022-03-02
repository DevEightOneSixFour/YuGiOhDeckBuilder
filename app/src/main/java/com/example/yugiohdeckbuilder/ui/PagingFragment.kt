package com.example.yugiohdeckbuilder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yugiohdeckbuilder.databinding.CardListPagesBinding
import com.example.yugiohdeckbuilder.presentation.CardViewModel
import com.example.yugiohdeckbuilder.utils.PAGE_SIZE

class PagingFragment: Fragment() {
    private lateinit var binding: CardListPagesBinding

    private val viewModel = ViewModelProvider(requireActivity())[CardViewModel::class.java]

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = CardListPagesBinding.inflate(layoutInflater)
//        return binding.root
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.ivNextPage.setOnClickListener {
//            viewModel.updateOffset(PAGE_SIZE)
//            Toast.makeText(context, "Clicked Next", Toast.LENGTH_SHORT).show()
//        }
//        binding.ivPreviousPage.setOnClickListener {
//            viewModel.updateOffset(-PAGE_SIZE)
//            Toast.makeText(context, "Clicked Back", Toast.LENGTH_SHORT).show()
//        }
//
//    }
}
