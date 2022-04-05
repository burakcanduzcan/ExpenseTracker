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

        if (e.period != "None") {
            var strBuilder = context.getString(R.string.expected_to_be_paid) + " "
            when (e.period) {
                "Yearly" -> strBuilder += context.getString(R.string.yearly)
                "Monthly" -> strBuilder += context.getString(R.string.monthly)
                "Weekly" -> strBuilder += context.getString(R.string.weekly)
                else -> {
                    strBuilder += "ERR0R"
                }
            }
            binding.tvPaymentPeriod.text = strBuilder
        } else {
            binding.tvPaymentPeriod.text = ""
        }

        if (e.paymentDay != 0) {
            val strBuilder = context.getString(R.string.on_every) + " " + e.paymentDay.toString()
            binding.tvPaymentDay.text = strBuilder
        } else {
            binding.tvPaymentDay.text = ""
        }
    }
}
