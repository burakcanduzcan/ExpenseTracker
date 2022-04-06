package com.burakcanduzcan.expensetracker.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.burakcanduzcan.expensetracker.model.Expense
import com.burakcanduzcan.expensetracker.model.ExpensePayment
import com.burakcanduzcan.expensetracker.model.tableNameExpensePayment
import com.burakcanduzcan.expensetracker.model.tableNameExpense

class ExpenseOperation(context: Context) {
    var db: SQLiteDatabase? = null
    var dbOpenHelper: DatabaseOpenHelper

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "ExpenseDB", null, 1)
    }

    fun open() {
        db = dbOpenHelper.writableDatabase
    }

    fun close() {
        if (db != null && db!!.isOpen) {
            db!!.close()
        }
    }

    fun addExpense(e: Expense): Long {
        val cv = ContentValues()
        cv.put("title", e.title)
        cv.put("period", e.period)
        cv.put("paymentDay", e.paymentDay)
        open()
        var output: Long = -1
        try {
            output = db!!.insert(tableNameExpense, null, cv)
        } catch (error: SQLiteException) {
            Log.e("Exception", "SQLException" + error.localizedMessage)
        }
        close()
        return output
    }

    fun addExpensePayment(ep: ExpensePayment): Long {
        val cv = ContentValues()
        cv.put("title", ep.title)
        cv.put("amount", ep.amount)
        cv.put("date", ep.date)
        open()
        var output: Long = -1
        try {
            output = db!!.insert(tableNameExpensePayment, null, cv)
        } catch (error: SQLiteException) {
            Log.e("Exception", "SQLException" + error.localizedMessage)
        }
        close()
        return output
    }

    fun updateExpense(e: Expense): Int {
        val cv = ContentValues()
        cv.put("title", e.title)
        cv.put("period", e.period)
        cv.put("paymentDay", e.paymentDay)
        open()
        val output = db!!.update(tableNameExpense, cv, "Id = ?", arrayOf(e.id.toString()))
        close()
        return output
    }

    fun updateExpensePayment(ep: ExpensePayment): Int {
        val cv = ContentValues()
        cv.put("title", ep.title)
        cv.put("amount", ep.amount)
        cv.put("date", ep.date)
        open()
        val output = db!!.update(tableNameExpensePayment, cv, "Id = ?", arrayOf(ep.id.toString()))
        close()
        return output
    }

    fun deleteExpense(id: Int) {
        open()
        db!!.delete(tableNameExpense, "id = ?", arrayOf(id.toString()))
        close()
    }

    fun deleteExpensePayment(id: Int) {
        open()
        db!!.delete(tableNameExpensePayment, "id = ?", arrayOf(id.toString()))
        close()
    }

    fun rawQueryGetAllFromTable(table: String): Cursor {
        val query = "SELECT * FROM $table"
        return db!!.rawQuery(query, null)
    }

    fun rawQueryGetExpensePaymentsFromTitle(title: String): Cursor {
        val query = "SELECT * FROM $tableNameExpensePayment WHERE title = ?"
        return db!!.rawQuery(query, arrayOf(title))
    }

    fun getExpenseFromTitle(title: String): Cursor {
        val query = "SELECT * FROM $tableNameExpense WHERE title = ?"
        return db!!.rawQuery(query, arrayOf(title))
    }

    @SuppressLint("Range")
    fun getExpenseFromId(inputId: Int): Expense {
        open()
        val query = "SELECT * FROM $tableNameExpense WHERE id = ?"
        val c: Cursor = db!!.rawQuery(query, arrayOf(inputId.toString()))
        c.moveToFirst()
        val returnExpense: Expense = Expense(
            c.getString(c.getColumnIndex("title")),
            c.getString(c.getColumnIndex("period")),
            c.getInt(3)
        ).apply {
            id = c.getInt(0)
        }
        c.close()
        close()
        return returnExpense
    }

    @SuppressLint("Range")
    fun fillListWithExpenses(inputList: ArrayList<Expense>) {
        open()
        val c: Cursor = rawQueryGetAllFromTable(tableNameExpense)
        if (c.moveToFirst()) {
            do {
                inputList.add(
                    Expense(
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("period")),
                        c.getInt(3)
                    ).apply {
                        id = c.getInt(0)
                    }
                )
            } while (c.moveToNext())
        }
        close()
    }

    @SuppressLint("Range")
    fun fillListWithExpensePayments(title: String, inputList: ArrayList<ExpensePayment>) {
        open()
        val c: Cursor = rawQueryGetExpensePaymentsFromTitle(title)
        if (c.moveToFirst()) {
            do {
                inputList.add(
                    ExpensePayment(
                        c.getString(c.getColumnIndex("title")),
                        c.getFloat(2),
                        c.getString(c.getColumnIndex("date"))
                    ).apply {
                        id = c.getInt(0)
                    }
                )
            } while (c.moveToNext())
        }
        close()
    }
}