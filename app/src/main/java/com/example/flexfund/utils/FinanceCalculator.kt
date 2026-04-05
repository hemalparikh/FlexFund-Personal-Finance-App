package com.example.flexfund.utils

import com.example.flexfund.model.Transaction

object FinanceCalculator {

    fun getNumericAmount(amount: String): Double {
        return amount
            .replace("+", "")
            .replace("-", "")
            .replace("₹", "")
            .replace("$", "")
            .replace("€", "")
            .replace("£", "")
            .replace("¥", "")
            .replace(",", "")
            .trim()
            .toDoubleOrNull() ?: 0.0
    }

    fun getTotalIncome(transactions: List<Transaction>): Double {
        return transactions.filter { it.isIncome }.sumOf { getNumericAmount(it.amount) }
    }

    fun getTotalExpense(transactions: List<Transaction>): Double {
        return transactions.filter { !it.isIncome }.sumOf { getNumericAmount(it.amount) }
    }

    fun getCurrentBalance(transactions: List<Transaction>): Double {
        return getTotalIncome(transactions) - getTotalExpense(transactions)
    }

    fun getTopSpendingCategory(transactions: List<Transaction>): String {
        val expenses = transactions.filter { !it.isIncome }

        if (expenses.isEmpty()) return "No Expenses Yet"

        val grouped = expenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { getNumericAmount(it.amount) } }

        return grouped.maxByOrNull { it.value }?.key ?: "No Data"
    }

    fun getTopSpendingAmount(transactions: List<Transaction>): Double {
        val expenses = transactions.filter { !it.isIncome }

        if (expenses.isEmpty()) return 0.0

        val grouped = expenses.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { getNumericAmount(it.amount) } }

        return grouped.maxByOrNull { it.value }?.value ?: 0.0
    }

    fun getSavingsProgress(transactions: List<Transaction>, goal: Double): Int {
        if (goal <= 0) return 0
        val balance = getCurrentBalance(transactions)
        return ((balance / goal) * 100).toInt().coerceIn(0, 100)
    }
}