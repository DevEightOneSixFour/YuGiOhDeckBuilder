package com.sdbfof.yugiohdeckbuilder.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.sdbfof.yugiohdeckbuilder.R
import com.sdbfof.yugiohdeckbuilder.databinding.CustomFilterAlertBinding
import com.sdbfof.yugiohdeckbuilder.ui.cards.FilterFragment

class CustomAlertView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding = CustomFilterAlertBinding.inflate(
        LayoutInflater.from(context),this, false)

    private val binding: CustomFilterAlertBinding
        get() = _binding

    private val builder = AlertDialog.Builder(context).create()

    fun buildUpLogoutAlertView(yuserName: String?, fragmentId: Int, activity: MainActivity) {
        with(builder) {
            binding.apply {
                tvFilterAlertTitle.text = resources.getString(R.string.logout_title)
                tvFilterAlertList.text = resources.getString(
                    R.string.logout_yuser,
                    yuserName
                )
                btnFilterAlertDismiss.apply {
                    text = resources.getString(R.string.logout_cancel)
                    setOnClickListener { builder.dismiss() }
                }
                btnFilterAlertSearch.apply {
                    text = resources.getString(R.string.logout_confirm)
                    setOnClickListener {
                        builder.dismiss()
                        activity.moveToLogin(fragmentId)
                    }
                }
            }
            setView(binding.root)
            show()
        }
    }

    fun buildFilterAlertView(list: List<String>, filter: FilterFragment) {
        with(builder) {
            binding.apply {
                if (list.isEmpty()) {
                    tvFilterAlertTitle.text =
                        resources.getString(R.string.selected_filter_empty_title)
                    tvFilterAlertList.text =
                        resources.getString(R.string.selected_filter_empty_list)
                    btnFilterAlertSearch.text =
                        resources.getString(R.string.selected_filter_empty_button)
                } else {
                    tvFilterAlertList.text = list.joinToString(" | ")
                }
                btnFilterAlertSearch.setOnClickListener {
                    builder.dismiss()
                    filter.moveToCardList()
                }
                btnFilterAlertDismiss.setOnClickListener {
                    builder.dismiss()
                }
            }
            setView(binding.root)
            show()
        }
    }
}