package com.burakcanduzcan.expensetracker.ui.newExpense

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityNewExpenseBinding
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.ui.expenseList.ExpenseListActivity

class NewExpenseActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewExpenseBinding
    lateinit var dbOperation: ExpenseOperation

    var incomingExpense: Expense? = null
    var doesItExist: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initalizeViews()
        initializeEvents()
        setDefaults()
    }

    private fun initalizeViews() {
        binding = ActivityNewExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listPreDefinedPeriods = arrayListOf("None", "Yearly", "Monthly", "Weekly")
        val adp: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listPreDefinedPeriods)
        binding.spNewExpensePeriod.adapter = adp
        binding.spNewExpensePeriod.setSelection(0)
    }

    private fun initializeEvents() {
        binding.btnNewExpenseSave.setOnClickListener {
            if (doesItExist) {
                //UPDATE
            } else {
                //CREATE
            }
        }

        binding.btnNewExpenseRemove.setOnClickListener {
            if (doesItExist)
                dbOperation.deleteExpense(incomingExpense!!.id)
        }
    }

    private fun setDefaults() {
        dbOperation = ExpenseOperation(this)

        incomingExpense = intent.getSerializableExtra("expense") as Expense?
        doesItExist = incomingExpense != null

        if (doesItExist) {
            /*
            binding.btnNewExpenseRemove.visibility = View.VISIBLE
            binding.etNewExpenseTitle.setText(incomingExpense!!.title)
            when (incomingExpense!!.period) {
                "Yearly" -> binding.spNewExpensePeriod.setSelection(1)
                "Monthly" -> binding.spNewExpensePeriod.setSelection(2)
                "Weekly" -> binding.spNewExpensePeriod.setSelection(3)
                else -> {
                    binding.spNewExpensePeriod.setSelection(0)
                }
            }
            binding.etNewExpensePaymentDay.setText(incomingExpense!!.paymentDay as Int)
             */
        } else {
            binding.btnNewExpenseRemove.visibility = View.INVISIBLE
        }
    }

    private fun isPaymentDayValid(period: String, paymentDay: Int): Boolean {
        when (period) {
            "None" -> {
                return paymentDay == 0
            }
            "Yearly" -> {
                return paymentDay in 1..12
            }
            "Monthly" -> {
                return paymentDay in 1..31
            }
            "Weekly" -> return paymentDay in 1..7
        }
        return false
    }
}