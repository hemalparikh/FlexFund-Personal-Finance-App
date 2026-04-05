package com.example.flexfund.ui.transactions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flexfund.R
import com.example.flexfund.databinding.FragmentTransactionsBinding
import com.example.flexfund.model.Transaction
import com.example.flexfund.utils.TransactionStorage
import java.io.File

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionList: MutableList<Transaction>

    private var selectedFilter = "ALL"
    private var searchQuery = ""

    private val addTransactionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val transaction =
                    result.data?.getSerializableExtra("new_transaction") as? Transaction
                val editIndex = result.data?.getIntExtra("edit_index", -1) ?: -1

                transaction?.let {
                    if (editIndex != -1) {
                        adapter.updateTransaction(editIndex, it)
                        Toast.makeText(requireContext(), "Transaction updated ✨", Toast.LENGTH_SHORT).show()
                    } else {
                        adapter.addTransaction(it)
                        binding.rvTransactions.smoothScrollToPosition(0)
                        Toast.makeText(requireContext(), "New transaction added 🎉", Toast.LENGTH_SHORT).show()
                    }

                    TransactionStorage.saveTransactions(requireContext(), adapter.getAllTransactions())
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionList = TransactionStorage.loadTransactions(requireContext())

        if (transactionList.isEmpty()) {
            transactionList = mutableListOf(
                Transaction("Salary", "Income", "+ ₹40,000", "10 Apr", true),
                Transaction("Zomato", "Food", "- ₹450", "12 Apr", false),
                Transaction("Uber", "Travel", "- ₹220", "13 Apr", false),
                Transaction("Freelance", "Income", "+ ₹5,000", "14 Apr", true),
                Transaction("Netflix", "Entertainment", "- ₹199", "15 Apr", false),
                Transaction("Shopping", "Lifestyle", "- ₹1,250", "16 Apr", false)
            )
            TransactionStorage.saveTransactions(requireContext(), transactionList)
            updateEmptyState()
        }

        adapter = TransactionAdapter(
            transactionList,
            onLongClickDelete = { index ->
                showDeleteDialog(index)
            },
            onClickEdit = { index ->
                openEditScreen(index)
            }
        )

        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.adapter = adapter
        updateEmptyState()

        binding.fabAddTransaction.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            addTransactionLauncher.launch(intent)
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnExport.setOnClickListener {
            exportTransactions()
        }

        binding.chipAll.setOnClickListener {
            selectedFilter = "ALL"
            updateFilterUI()
        }

        binding.chipIncome.setOnClickListener {
            selectedFilter = "INCOME"
            updateFilterUI()
        }

        binding.chipExpense.setOnClickListener {
            selectedFilter = "EXPENSE"
            updateFilterUI()
        }

        binding.etSearch.addTextChangedListener {
            searchQuery = it.toString()
            adapter.filter(selectedFilter, searchQuery)
        }

        updateFilterUI()
    }

    private fun openEditScreen(index: Int) {
        val transaction = adapter.getAllTransactions()[index]
        val intent = Intent(requireContext(), AddTransactionActivity::class.java).apply {
            putExtra("edit_transaction", transaction)
            putExtra("edit_index", index)
        }
        addTransactionLauncher.launch(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun showDeleteDialog(index: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Delete") { _, _ ->
                adapter.deleteTransaction(index)
                TransactionStorage.saveTransactions(requireContext(), adapter.getAllTransactions())
                updateEmptyState()
                Toast.makeText(requireContext(), "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateFilterUI() {
        binding.chipAll.setTextColor(resources.getColor(com.example.flexfund.R.color.text_secondary))
        binding.chipIncome.setTextColor(resources.getColor(com.example.flexfund.R.color.text_secondary))
        binding.chipExpense.setTextColor(resources.getColor(com.example.flexfund.R.color.text_secondary))

        when (selectedFilter) {
            "ALL" -> binding.chipAll.setTextColor(resources.getColor(com.example.flexfund.R.color.neon_blue))
            "INCOME" -> binding.chipIncome.setTextColor(resources.getColor(com.example.flexfund.R.color.neon_green))
            "EXPENSE" -> binding.chipExpense.setTextColor(resources.getColor(com.example.flexfund.R.color.soft_red))
        }

        adapter.filter(selectedFilter, searchQuery)
    }

    private fun updateEmptyState() {
        if (adapter.getAllTransactions().isEmpty()) {
            binding.layoutEmptyState.visibility = View.VISIBLE
            binding.rvTransactions.visibility = View.GONE
        } else {
            binding.layoutEmptyState.visibility = View.GONE
            binding.rvTransactions.visibility = View.VISIBLE
        }
    }

    private fun exportTransactions() {
        val transactions = adapter.getAllTransactions()

        if (transactions.isEmpty()) {
            Toast.makeText(requireContext(), "No transactions to export", Toast.LENGTH_SHORT).show()
            return
        }

        val content = StringBuilder()
        content.append("FlexFund - Exported Transactions\n")
        content.append("====================================\n\n")

        transactions.forEachIndexed { index, transaction ->
            content.append("${index + 1}. ${transaction.title}\n")
            content.append("Category: ${transaction.category}\n")
            content.append("Amount: ${transaction.amount}\n")
            content.append("Date: ${transaction.date}\n")
            content.append("Type: ${if (transaction.isIncome) "Income" else "Expense"}\n")
            content.append("\n------------------------------------\n\n")
        }

        try {
            val file = File(requireContext().cacheDir, "flexfund_transactions.txt")
            file.writeText(content.toString())

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, androidx.core.content.FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider",
                    file
                ))
                putExtra(Intent.EXTRA_SUBJECT, "FlexFund Transactions Export")
                putExtra(Intent.EXTRA_TEXT, "Here is my exported transaction summary from FlexFund.")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Export Transactions"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}