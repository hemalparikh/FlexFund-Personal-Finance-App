package com.example.flexfund.utils

import android.content.Context

object AppSettings {

    private const val PREF_NAME = "flexfund_settings"

    private const val KEY_USER_NAME = "user_name"
    private const val KEY_SAVINGS_GOAL = "savings_goal"
    private const val KEY_CURRENCY = "currency"
    private const val KEY_NOTIFICATIONS = "notifications"

    fun saveUserName(context: Context, name: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_USER_NAME, name)
            .apply()
    }

    fun getUserName(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_NAME, "Hemal") ?: "Hemal"
    }

    fun saveSavingsGoal(context: Context, goal: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_SAVINGS_GOAL, goal)
            .apply()
    }

    fun getSavingsGoal(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_SAVINGS_GOAL, "50000") ?: "50000"
    }

    fun saveCurrency(context: Context, currency: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CURRENCY, currency)
            .apply()
    }

    fun getCurrency(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENCY, "INR (₹)") ?: "INR (₹)"
    }

    fun saveNotifications(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_NOTIFICATIONS, enabled)
            .apply()
    }

    fun getNotifications(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_NOTIFICATIONS, false)
    }

    fun getCurrencySymbol(context: Context): String {
        return when (getCurrency(context)) {
            "USD ($)" -> "$"
            "EUR (€)" -> "€"
            "GBP (£)" -> "£"
            "JPY (¥)" -> "¥"
            else -> "₹"
        }
    }
}