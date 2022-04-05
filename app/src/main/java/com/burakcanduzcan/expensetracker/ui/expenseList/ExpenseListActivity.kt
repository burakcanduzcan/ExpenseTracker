package com.burakcanduzcan.expensetracker.ui.expenseList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityExpenseListBinding
import com.burakcanduzcan.expensetracker.model.listExpense
import com.burakcanduzcan.expensetracker.ui.addPayment.AddPaymentActivity
import com.burakcanduzcan.expensetracker.ui.newExpense.NewExpenseActivity
import com.burakcanduzcan.expensetracker.ui.expenseDetail.ExpenseDetailActivity

class ExpenseListActivity : AppCompatActivity() {
    lateinit var binding: ActivityExpenseListBinding
    lateinit var dbOperation: ExpenseOperation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()
    }

    override fun onResume() {
        super.onResume()
        binding.rvExpenseList.adapter!!.notifyDataSetChanged()
    }

    private fun initializeViews() {
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvExpenseList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun initializeEvents() {
        binding.btnAddNewExpenseType.setOnClickListener {
            val intent = Intent(this, NewExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setDefaults() {
        listExpense.clear()

        dbOperation = ExpenseOperation(this)
        dbOperation.fillListWithExpenses(listExpense)

        binding.rvExpenseList.adapter = ExpenseAdapter(applicationContext, listExpense, ::addPaymentClick, ::itemClick)
    }

    private fun addPaymentClick(i: Int) {
        val intent = Intent(this, AddPaymentActivity::class.java)
        intent.putExtra("expenseId", listExpense[i].id)
        startActivity(intent)
    }

    private fun itemClick(i: Int) {
        val intent = Intent(this, ExpenseDetailActivity::class.java)
        intent.putExtra("expenseId", listExpense[i].id)
        startActivity(intent)
    }

}
