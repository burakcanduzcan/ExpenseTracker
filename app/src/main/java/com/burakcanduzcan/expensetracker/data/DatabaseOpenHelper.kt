package com.burakcanduzcan.expensetracker.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p0: SQLiteDatabase) {
        val query1 = "CREATE TABLE Expense(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, period TEXT, paymentDay INTEGER)"
        p0.execSQL(query1)
        val query2 = "CREATE TABLE ExpensePayment(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, amount REAL, date TEXT)"
        p0.execSQL(query2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p1 == 1) {

        } else if (p1 == 2) {

        }
    }
}