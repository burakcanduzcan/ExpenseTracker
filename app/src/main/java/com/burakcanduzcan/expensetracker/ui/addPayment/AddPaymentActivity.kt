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

        val incomingTitle = intent.getIntExtra("expenseId", -1)
        Toast.makeText(this, incomingTitle.toString(), Toast.LENGTH_SHORT).show()
    }
}