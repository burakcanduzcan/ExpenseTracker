package com.burakcanduzcan.expensetracker.ui.expenseDetail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.expensetracker.R
import com.burakcanduzcan.expensetracker.data.ExpenseOperation
import com.burakcanduzcan.expensetracker.databinding.ActivityExpenseDetailBinding
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.model.listExpensePayment
import com.burakcanduzcan.expensetracker.ui.addPayment.AddPaymentActivity
import com.burakcanduzcan.expensetracker.ui.newExpense.NewExpenseActivity

class ExpenseDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseDetailBinding
    private lateinit var dbOperation: ExpenseOperation

    private lateinit var incomingExpense: Expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()
    }

    private fun initializeViews() {
        binding = ActivityExpenseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvExpensePayment.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
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

    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = ExpenseOperation(this)
        val incomingId = intent.getIntExtra("expenseId", -1)
        incomingExpense = dbOperation.getExpenseFromId(incomingId)

        listExpensePayment.clear()
        dbOperation.fillListWithExpensePayments(incomingExpense.title, listExpensePayment)

        binding.rvExpensePayment.adapter =
            ExpensePaymentAdapter(applicationContext, listExpensePayment, ::itemClick)
    }

    private fun itemClick(i: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.Delete_Expense_Payment))
            .setMessage(getString(R.string.this_will_erase_selected_expense_payment_from_database))
            .setPositiveButton(getString(R.string.Continue)) { _, _ ->
                dbOperation.deleteExpensePayment(listExpensePayment[i].id)
                Toast.makeText(
                    this,
                    getString(R.string.payment_deletion_success),
                    Toast.LENGTH_SHORT
                ).show()
                listExpensePayment.removeAt(i)
                binding.rvExpensePayment.adapter!!.notifyItemRemoved(i)
            }
            .setNegativeButton(getString(R.string.Cancel), null).show()
    }
}
