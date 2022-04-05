package com.burakcanduzcan.expensetracker.model

import java.io.Serializable
import kotlin.properties.Delegates

var listExpense = ArrayList<Expense>()
var tableNameExpense = "Expense"

class Expense(var title: String) : Serializable{
    var id by Delegates.notNull<Int>()
    var period: String? = null
    var paymentDay: Int? = null

    constructor(title: String, period: String, paymentDay: Int) : this(title) {
        this.period = period
        this.paymentDay = paymentDay
    }
}