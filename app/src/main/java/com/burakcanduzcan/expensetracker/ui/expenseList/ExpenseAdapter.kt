package com.burakcanduzcan.expensetracker.ui.expenseList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.databinding.RvcExpenseBinding

class ExpenseAdapter(
    val context: Context,
    var list: ArrayList<Expense>,
    var addPaymentClick: (position: Int) -> Unit,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val from = LayoutInflater.from(context)
        val binding = RvcExpenseBinding.inflate(from, parent, false)
        return ExpenseViewHolder(context, binding, addPaymentClick, itemClick)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bindData(list.get(position), context)
    }

    override fun getItemCount(): Int = list.size
}