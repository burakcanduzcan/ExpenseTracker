package com.burakcanduzcan.expensetracker.ui.expenseDetail

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.databinding.RvcExpensePaymentBinding
import com.burakcanduzcan.expensetracker.model.ExpensePayment

class ExpensePaymentViewHolder(
    private val binding: RvcExpensePaymentBinding,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clExpensePayment.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(ep: ExpensePayment, context: Context) {
        binding.tvPayment.text = ep.amount.toString() + " " + context.getString(R.string.turkish_lira_symbol) + ","
        binding.tvDate.text = context.getString(R.string.paid_on) + " " + ep.date
    }
}