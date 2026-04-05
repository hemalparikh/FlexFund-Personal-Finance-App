package com.example.flexfund.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flexfund.databinding.FragmentProfileBinding
import com.example.flexfund.utils.AppSettings

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCurrencyDropdown()
        loadSavedSettings()
        setupSaveButton()
    }

    private fun setupCurrencyDropdown() {
        val currencies = listOf("INR (₹)", "USD ($)", "EUR (€)", "GBP (£)", "JPY (¥)")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, currencies)
        binding.autoCurrency.setAdapter(adapter)

        binding.autoCurrency.setOnClickListener {
            binding.autoCurrency.showDropDown()
        }
    }

    private fun loadSavedSettings() {
        binding.etUserName.setText(AppSettings.getUserName(requireContext()))
        binding.etSavingsGoal.setText(AppSettings.getSavingsGoal(requireContext()))
        binding.autoCurrency.setText(AppSettings.getCurrency(requireContext()), false)
        binding.switchNotifications.isChecked = AppSettings.getNotifications(requireContext())
    }

    private fun setupSaveButton() {
        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etUserName.text.toString().trim()
            val goal = binding.etSavingsGoal.text.toString().trim()
            val currency = binding.autoCurrency.text.toString().trim()
            val notifications = binding.switchNotifications.isChecked

            AppSettings.saveUserName(requireContext(), name)
            AppSettings.saveSavingsGoal(requireContext(), goal)
            AppSettings.saveCurrency(requireContext(), currency)
            AppSettings.saveNotifications(requireContext(), notifications)

            Toast.makeText(requireContext(), "Settings saved successfully ⚙️", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}