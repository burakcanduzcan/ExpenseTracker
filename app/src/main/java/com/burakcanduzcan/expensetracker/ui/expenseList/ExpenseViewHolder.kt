package com.burakcanduzcan.expensetracker.ui.expenseList

import android.content.Context
import android.text.SpannableString
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.databinding.RvcExpenseBinding

class ExpenseViewHolder(
    private val binding: RvcExpenseBinding,
    var addPaymentClick: (position: Int) -> Unit,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnAddPayment.setOnClickListener {
            addPaymentClick(adapterPosition)
        }
        binding.clExpense.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(e: Expense, context: Context) {
        binding.tvExpenseTitle.setText(e.title)

        //when the expense is expected to be paid periodically
        if (e.period != null) {
            var strBuilder = context.getString(R.string.expected_to_be_paid) + " "
            when (e.period) {
                "Yearly" -> strBuilder += context.getString(R.string.yearly)
                "Monthly" -> strBuilder += context.getString(R.string.monthly)
                "Weekly" -> strBuilder += context.getString(R.string.weekly)
                else -> {
                    strBuilder += "ERROR"
                }
            }
            binding.tvPaymentPeriod.text = strBuilder
        } else {
            binding.tvPaymentPeriod.text = ""
        }

        //when the expense is expected to be paid periodically and given a payment day
        //*** to do, calculate payment day
        //if (e.period != null && e.paymentDay != null) {
        if (e.paymentDay != null) {
            var strBuilder = context.getString(R.string.on_every) + " " + e.paymentDay.toString()
            binding.tvPaymentDay.text = strBuilder
        } else {
            binding.tvPaymentDay.text = ""
        }
    }
}
