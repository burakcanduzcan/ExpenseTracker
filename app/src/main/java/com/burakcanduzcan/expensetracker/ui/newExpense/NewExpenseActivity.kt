package com.burakcanduzcan.expensetracker.ui.newExpense

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityNewExpenseBinding
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.ui.expenseDetail.ExpenseDetailActivity
import com.burakcanduzcan.expensetracker.ui.expenseList.ExpenseListActivity

class NewExpenseActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewExpenseBinding
    lateinit var dbOperation: ExpenseOperation

    lateinit var incomingExpense: Expense
    private var doesExpenseExists: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()
    }

    private fun initializeViews() {
        binding = ActivityNewExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listPreDefinedPeriods = arrayListOf("None", "Yearly", "Monthly", "Weekly")
        val adp: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listPreDefinedPeriods)
        binding.spNewExpensePeriod.adapter = adp
    }

    private fun initializeEvents() {
        binding.btnNewExpenseSave.setOnClickListener {
            if (doesExpenseExists) {
                updateExpense()
            } else {
                createExpense()
            }
        }

        binding.btnNewExpenseRemove.setOnClickListener {
            if (doesExpenseExists) {
                val adb: AlertDialog.Builder = AlertDialog.Builder(this)
                adb.setTitle("Delete Expense")
                adb.setMessage("This will erase current expense from database")
                adb.setPositiveButton(
                    "Continue",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        dbOperation.deleteExpense(incomingExpense.id)
                        Toast.makeText(this, getString(R.string.expense_deletion_success), Toast.LENGTH_SHORT).show()
                        val intentToExpenseDetail = Intent(this, ExpenseListActivity::class.java)
                        startActivity(intentToExpenseDetail)
                    })
                adb.setNegativeButton("Cancel", null)
                adb.show()
            }
        }
    }

    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = ExpenseOperation(this)
        val incomingId: Int = intent.getIntExtra("expenseId", -1)

        doesExpenseExists = incomingId != -1
        if (doesExpenseExists) {
            incomingExpense = dbOperation.getExpenseFromId(incomingId)
            updateViewsForUpdate()
        } else {
            binding.btnNewExpenseRemove.visibility = View.INVISIBLE
        }
    }

    private fun updateViewsForUpdate() {
        binding.btnNewExpenseRemove.visibility = View.VISIBLE
        binding.etNewExpenseTitle.setText(incomingExpense.title)
        when (incomingExpense.period) {
            "Yearly" -> binding.spNewExpensePeriod.setSelection(1)
            "Monthly" -> binding.spNewExpensePeriod.setSelection(2)
            "Weekly" -> binding.spNewExpensePeriod.setSelection(3)
            else -> {
                binding.spNewExpensePeriod.setSelection(0)
            }
        }
        binding.etNewExpensePaymentDay.setText(incomingExpense.paymentDay.toString())
    }

    private fun isPaymentDayValid(period: String, paymentDay: Int): Boolean {
        when (period) {
            "Yearly" -> {
                return paymentDay in 1..12
            }
            "Monthly" -> {
                return paymentDay in 1..31
            }
            "Weekly" -> {
                return paymentDay in 1..7
            }
        }
        return false
    }

    private fun updateExpense() {
        TODO("Not yet implemented")
    }

    private fun createExpense() {
        val tmpTitle: String = binding.etNewExpenseTitle.text.toString()
        val tmpPeriod: String = binding.spNewExpensePeriod.selectedItem.toString()
        val tmpPaymentDay: Int = binding.etNewExpensePaymentDay.text.toString().toIntOrNull() ?: -1

        if (tmpTitle != "") {
            if (tmpPeriod.equals("None")) {
                //just title
                dbOperation.addExpense(Expense(tmpTitle, tmpPeriod, 0))
                Toast.makeText(this, getString(R.string.expense_creation_success), Toast.LENGTH_SHORT).show()
                val intentToExpenseDetail = Intent(this, ExpenseListActivity::class.java)
                startActivity(intentToExpenseDetail)
            } else {
                //all 3 of them are supplied
                if (isPaymentDayValid(tmpPeriod, tmpPaymentDay)) {
                    dbOperation.addExpense(Expense(tmpTitle, tmpPeriod, tmpPaymentDay))
                    Toast.makeText(this, getString(R.string.expense_creation_success), Toast.LENGTH_SHORT).show()
                    val intentToExpenseDetail = Intent(this, ExpenseListActivity::class.java)
                    startActivity(intentToExpenseDetail)
                } else {
                    Toast.makeText(this, getString(R.string.invalid_period_or_payment_day), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.title_cant_be_empty), Toast.LENGTH_SHORT).show()
        }
    }
}