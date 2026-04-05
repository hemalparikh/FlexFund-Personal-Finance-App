package com.example.flexfund.model

import java.io.Serializable

data class Transaction(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val isIncome: Boolean
) : Serializable