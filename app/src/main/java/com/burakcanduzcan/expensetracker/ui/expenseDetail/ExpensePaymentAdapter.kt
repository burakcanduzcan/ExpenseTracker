package com.burakcanduzcan.expensetracker.ui.expenseDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.expensetracker.databinding.RvcExpensePaymentBinding
import com.burakcanduzcan.expensetracker.model.ExpensePayment

class ExpensePaymentAdapter(
    val context: Context,
    var list: ArrayList<ExpensePayment>,
    var itemClick: (position: Int) -> Unit
    ): RecyclerView.Adapter<ExpensePaymentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensePaymentViewHolder {
        val from = LayoutInflater.from(context)
        val binding = RvcExpensePaymentBinding.inflate(from, parent, false)
        return ExpensePaymentViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ExpensePaymentViewHolder, position: Int) {
        holder.bindData(list.get(position), context)
    }

    override fun getItemCount(): Int = list.size
}