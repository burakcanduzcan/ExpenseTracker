package com.burakcanduzcan.expensetracker.ui.addPayment

import android.annotation.SuppressLint
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityAddPaymentBinding
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.model.ExpensePayment
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentBinding
    lateinit var incomingExpense: Expense
    private lateinit var dbOperation: ExpenseOperation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()
    }

    private fun initializeViews() {
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeEvents() {
        val today = Calendar.getInstance()
        binding.dpPaymentDate.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            binding.tvCurrentDate.text = "${getString(R.string.Selected_date_is)} ${getProperDateText()}"
        }

        binding.btnSavePayment.setOnClickListener {
            if (!binding.etAddPaymentAmount.text.equals("")) {
                dbOperation.addExpensePayment(ExpensePayment(
                    incomingExpense.title,
                    binding.etAddPaymentAmount.text.toString().toFloat(),
                    getProperDateText()
                ))
                Toast.makeText(this, getString(R.string.payment_addition_success), Toast.LENGTH_SHORT).show()
                binding.etAddPaymentAmount.setText("")
                dbOperation.open()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_amount), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = ExpenseOperation(this)
        val incomingTitle: Int = intent.getIntExtra("expenseId", -1)
        incomingExpense = dbOperation.getExpenseFromId(incomingTitle)
        binding.dpPaymentDate.maxDate = Calendar.getInstance().timeInMillis
        binding.tvCurrentDate.setText("${getString(R.string.Selected_date_is)} ${getProperDateText()}")
    }

    private fun getProperDateText(): String {
        var returnString = ""

        val tmpDay: Int = binding.dpPaymentDate.dayOfMonth
        if (tmpDay in 1..9)
            returnString = returnString + "0" + tmpDay.toString() + "."
        else
            returnString = returnString + tmpDay.toString() + "."

        val tmpMonth: Int = binding.dpPaymentDate.month
        if (tmpMonth in 1..9)
            returnString = returnString + "0" + tmpDay.toString() + "."
        else
            returnString = returnString + tmpDay.toString() + "."

        returnString += binding.dpPaymentDate.year

        return returnString
    }
}