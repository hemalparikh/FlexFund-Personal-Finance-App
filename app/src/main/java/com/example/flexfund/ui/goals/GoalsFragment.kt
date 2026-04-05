package com.example.flexfund.ui.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flexfund.databinding.FragmentGoalsBinding
import com.example.flexfund.utils.AppSettings
import com.example.flexfund.utils.FinanceCalculator
import com.example.flexfund.utils.TransactionStorage

class GoalsFragment : Fragment() {

    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadGoalData()
    }

    private fun loadGoalData() {
        val transactions = TransactionStorage.loadTransactions(requireContext())
        val currency = AppSettings.getCurrencySymbol(requireContext())
        val goalValue = AppSettings.getSavingsGoal(requireContext()).toDoubleOrNull() ?: 50000.0

        val balance = FinanceCalculator.getCurrentBalance(transactions)
        val progress = FinanceCalculator.getSavingsProgress(transactions, goalValue)

        binding.progressGoal.progress = progress
        binding.tvProgressPercent.text = "$progress%"
        binding.tvGoalAmount.text = "$currency ${goalValue.toInt()}"
        binding.tvSaved.text = "Saved: $currency${balance.toInt()}"

        val remaining = (goalValue - balance).coerceAtLeast(0.0)

        binding.tvSmartSuggestion.text =
            if (balance >= goalValue) {
                "Amazing! You’ve already achieved your savings goal 🎉"
            } else {
                "You’re just $currency${remaining.toInt()} away from your target. Keep going 🚀"
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}