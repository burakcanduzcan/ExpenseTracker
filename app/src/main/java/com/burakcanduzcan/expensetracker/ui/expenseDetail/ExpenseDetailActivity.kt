package com.burakcanduzcan.expensetracker.ui.expenseDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityExpenseDetailBinding
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.model.listExpense
import com.burakcanduzcan.expensetracker.ui.addPayment.AddPaymentActivity
import com.burakcanduzcan.expensetracker.ui.newExpense.NewExpenseActivity

class ExpenseDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityExpenseDetailBinding
    lateinit var dbOperation: ExpenseOperation

    lateinit var incomingExpense: Expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()

    }

    private fun initializeViews() {
        binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeEvents() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, NewExpenseActivity::class.java)
            intent.putExtra("expenseId", incomingExpense.id)
            startActivity(intent)
        }

        binding.btnGoToAddPayment.setOnClickListener {
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("expenseId", incomingExpense.id)
            startActivity(intent)
        }
    }

    private fun setDefaults() {
        dbOperation = ExpenseOperation(this)
        val incomingId = intent.getIntExtra("expenseId", -1)
        incomingExpense = dbOperation.getExpenseFromId(incomingId)
        //Toast.makeText(this, incomingId.toString(), Toast.LENGTH_SHORT).show()
    }
}