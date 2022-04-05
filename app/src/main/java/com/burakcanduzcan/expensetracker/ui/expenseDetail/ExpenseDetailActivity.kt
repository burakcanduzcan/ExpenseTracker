package com.burakcanduzcan.expensetracker.ui.expenseDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakcanduzcan.expensetracker.databinding.ActivityExpenseDetailBinding
import com.burakcanduzcan.expensetracker.model.Expense

class ExpenseDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityExpenseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val incomingTitle = intent.getIntExtra("expenseId", -1)
        Toast.makeText(this, incomingTitle.toString(), Toast.LENGTH_SHORT).show()
    }
}