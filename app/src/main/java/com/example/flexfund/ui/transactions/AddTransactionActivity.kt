package com.example.flexfund.ui.transactions

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flexfund.R
import com.example.flexfund.databinding.ActivityAddTransactionBinding
import com.example.flexfund.model.Transaction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.example.flexfund.utils.AppSettings

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private var isIncomeSelected = false
    private val calendar = Calendar.getInstance()
    private var editIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoryDropdown()
        setupTypeToggle()
        setupDatePicker()
        setupClicks()
        setTodayDate()
        checkEditMode()
    }

    private fun setupCategoryDropdown() {
        val categories = listOf(
            "Food", "Travel", "Shopping", "Bills", "Entertainment",
            "Health", "Salary", "Freelance", "Investment", "Education", "Other"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        binding.autoCategory.setAdapter(adapter)
    }

    private fun setupTypeToggle() {
        isIncomeSelected = false
        updateToggleUI()

        binding.btnExpense.setOnClickListener {
            isIncomeSelected = false
            updateToggleUI()
        }

        binding.btnIncome.setOnClickListener {
            isIncomeSelected = true
            updateToggleUI()
        }
    }

    private fun updateToggleUI() {
        if (isIncomeSelected) {
            binding.btnIncome.alpha = 1f
            binding.btnExpense.alpha = 0.5f
        } else {
            binding.btnExpense.alpha = 1f
            binding.btnIncome.alpha = 0.5f
        }
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            openDatePicker()
        }

        binding.etDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) openDatePicker()
        }
    }

    private fun openDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                binding.etDate.setText(format.format(calendar.time))
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun setTodayDate() {
        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.etDate.setText(format.format(calendar.time))
    }

    private fun checkEditMode() {
        val transaction = intent.getSerializableExtra("edit_transaction") as? Transaction
        editIndex = intent.getIntExtra("edit_index", -1)

        transaction?.let {
            binding.etTitle.setText(it.title)
            val cleanedAmount = it.amount
                .replace("+ ₹", "")
                .replace("- ₹", "")
                .replace("+ $", "")
                .replace("- $", "")
                .replace("+ €", "")
                .replace("- €", "")
                .replace("+ £", "")
                .replace("- £", "")
                .replace("+ ¥", "")
                .replace("- ¥", "")

            binding.etAmount.setText(cleanedAmount)
            binding.autoCategory.setText(it.category, false)
            binding.etDate.setText(it.date)
            isIncomeSelected = it.isIncome
            updateToggleUI()
            binding.btnSaveTransaction.text = "Update Transaction"
        }
    }

    private fun setupClicks() {
        binding.ivBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        binding.btnSaveTransaction.setOnClickListener {
            if (validateInputs()) {
                saveTransactionAndReturn()
            }
        }
    }

    private fun saveTransactionAndReturn() {
        val title = binding.etTitle.text.toString().trim()
        val amountValue = binding.etAmount.text.toString().trim()
        val category = binding.autoCategory.text.toString().trim()
        val date = binding.etDate.text.toString().trim()

        val currencySymbol = AppSettings.getCurrencySymbol(this)

        val formattedAmount = if (isIncomeSelected) {
            "+ $currencySymbol$amountValue"
        } else {
            "- $currencySymbol$amountValue"
        }

        val transaction = Transaction(
            title = title,
            category = category,
            amount = formattedAmount,
            date = date,
            isIncome = isIncomeSelected
        )

        val resultIntent = Intent().apply {
            putExtra("new_transaction", transaction)
            putExtra("edit_index", editIndex)
        }

        setResult(RESULT_OK, resultIntent)

        Toast.makeText(
            this,
            if (editIndex != -1) "Transaction updated ✨" else "Transaction added 🎉",
            Toast.LENGTH_SHORT
        ).show()

        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun validateInputs(): Boolean {
        val title = binding.etTitle.text.toString().trim()
        val amount = binding.etAmount.text.toString().trim()
        val category = binding.autoCategory.text.toString().trim()
        val date = binding.etDate.text.toString().trim()

        when {
            title.isEmpty() -> {
                binding.etTitle.error = "Enter transaction title"
                binding.etTitle.requestFocus()
                return false
            }

            amount.isEmpty() -> {
                binding.etAmount.error = "Enter amount"
                binding.etAmount.requestFocus()
                return false
            }

            category.isEmpty() -> {
                binding.autoCategory.error = "Select category"
                binding.autoCategory.requestFocus()
                return false
            }

            date.isEmpty() -> {
                binding.etDate.error = "Select date"
                binding.etDate.requestFocus()
                return false
            }
        }

        return true
    }
}