package com.burakcanduzcan.expensetracker.ui.addPayment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakcanduzcan.expensetracker.databinding.ActivityAddPaymentBinding
import com.burakcanduzcan.expensetracker.model.Expense

class AddPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //var incomingExpense: Expense = intent.getSerializableExtra("expense") as Expense
        //Toast.makeText(this, incomingExpense.title, Toast.LENGTH_SHORT).show()
    }
}