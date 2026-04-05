package com.example.flexfund.utils

import android.content.Context
import com.example.flexfund.model.Transaction
import org.json.JSONArray
import org.json.JSONObject

object TransactionStorage {

    private const val PREF_NAME = "flexfund_prefs"
    private const val KEY_TRANSACTIONS = "transactions"

    fun saveTransactions(context: Context, transactions: List<Transaction>) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val jsonArray = JSONArray()
        transactions.forEach { transaction ->
            val jsonObject = JSONObject().apply {
                put("title", transaction.title)
                put("category", transaction.category)
                put("amount", transaction.amount)
                put("date", transaction.date)
                put("isIncome", transaction.isIncome)
            }
            jsonArray.put(jsonObject)
        }

        editor.putString(KEY_TRANSACTIONS, jsonArray.toString())
        editor.apply()
    }

    fun loadTransactions(context: Context): MutableList<Transaction> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString(KEY_TRANSACTIONS, null)

        val transactionList = mutableListOf<Transaction>()

        if (!jsonString.isNullOrEmpty()) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                transactionList.add(
                    Transaction(
                        title = obj.getString("title"),
                        category = obj.getString("category"),
                        amount = obj.getString("amount"),
                        date = obj.getString("date"),
                        isIncome = obj.getBoolean("isIncome")
                    )
                )
            }
        }

        return transactionList
    }
}