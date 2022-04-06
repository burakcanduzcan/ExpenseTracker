package com.burakcanduzcan.expensetracker.model

import kotlin.properties.Delegates

var listExpensesAndPayment = ArrayList<ExpensePayment>()
var tableNameExpensePayment = "ExpensePayment"

class ExpensePayment(
    var title: String,
    var amount: Float,
    var date: String
) {
    var id by Delegates.notNull<Int>()
}