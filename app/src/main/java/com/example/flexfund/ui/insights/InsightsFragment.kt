package com.example.flexfund.ui.insights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flexfund.databinding.FragmentInsightsBinding
import com.example.flexfund.utils.AppSettings
import com.example.flexfund.utils.FinanceCalculator
import com.example.flexfund.utils.TransactionStorage

class InsightsFragment : Fragment() {

    private var _binding: FragmentInsightsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadInsights()
    }

    private fun loadInsights() {
        val transactions = TransactionStorage.loadTransactions(requireContext())
        val currency = AppSettings.getCurrencySymbol(requireContext())

        val topCategory = FinanceCalculator.getTopSpendingCategory(transactions)
        val topAmount = FinanceCalculator.getTopSpendingAmount(transactions)
        val totalExpense = FinanceCalculator.getTotalExpense(transactions)

        binding.tvTopCategory.text = topCategory
        binding.tvTopAmount.text = "$currency${topAmount.toInt()} spent most"

        binding.tvWeeklyCompare.text =
            if (totalExpense > 5000) {
                "Your expenses are rising this week. Try cutting small daily spends ⚠️"
            } else {
                "You’re maintaining a healthy spending pattern this week 🎉"
            }

        binding.tvInsight.text =
            if (topCategory == "No Expenses Yet") {
                "Add some transactions to unlock smarter insights."
            } else {
                "Your highest spending is on $topCategory. Reducing it slightly can improve savings."
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}