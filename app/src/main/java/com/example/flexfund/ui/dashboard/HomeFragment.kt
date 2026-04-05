package com.example.flexfund.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flexfund.databinding.FragmentHomeBinding
import com.example.flexfund.utils.AppSettings
import com.example.flexfund.utils.FinanceCalculator
import com.example.flexfund.utils.TransactionStorage

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun loadDashboardData() {
        val transactions = TransactionStorage.loadTransactions(requireContext())
        val currency = AppSettings.getCurrencySymbol(requireContext())
        val userName = AppSettings.getUserName(requireContext())

        val income = FinanceCalculator.getTotalIncome(transactions)
        val expense = FinanceCalculator.getTotalExpense(transactions)
        val balance = FinanceCalculator.getCurrentBalance(transactions)

        binding.tvUserName.text = "$userName 👋"
        binding.tvBalance.text = "$currency${"%.0f".format(balance)}"
        binding.tvIncome.text = "+ $currency${"%.0f".format(income)}"
        binding.tvExpense.text = "- $currency${"%.0f".format(expense)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}