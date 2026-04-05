package com.example.flexfund.ui.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flexfund.R
import com.example.flexfund.databinding.ItemTransactionBinding
import com.example.flexfund.model.Transaction

class TransactionAdapter(
    private val fullList: MutableList<Transaction>,
    private val onLongClickDelete: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var filteredList: MutableList<Transaction> = fullList.toMutableList()

    inner class TransactionViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = filteredList[position]

        holder.binding.tvTransactionTitle.text = item.title
        holder.binding.tvTransactionCategory.text = "${item.category} • ${item.date}"
        holder.binding.tvTransactionAmount.text = item.amount

        if (item.isIncome) {
            holder.binding.tvTransactionAmount.setTextColor(
                holder.itemView.context.getColor(R.color.neon_green)
            )
            holder.binding.ivCategoryIcon.setImageResource(android.R.drawable.arrow_up_float)
            holder.binding.ivCategoryIcon.setColorFilter(
                holder.itemView.context.getColor(R.color.neon_green)
            )
        } else {
            holder.binding.tvTransactionAmount.setTextColor(
                holder.itemView.context.getColor(R.color.soft_red)
            )
            holder.binding.ivCategoryIcon.setImageResource(android.R.drawable.arrow_down_float)
            holder.binding.ivCategoryIcon.setColorFilter(
                holder.itemView.context.getColor(R.color.soft_red)
            )
        }

        holder.itemView.setOnClickListener {
            val actualIndex = fullList.indexOf(item)
            if (actualIndex != -1) {
                onClickEdit(actualIndex)
            }
        }

        holder.itemView.setOnLongClickListener {
            val actualIndex = fullList.indexOf(item)
            if (actualIndex != -1) {
                onLongClickDelete(actualIndex)
            }
            true
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun addTransaction(transaction: Transaction) {
        fullList.add(0, transaction)
        filter("ALL", "")
    }

    fun updateTransaction(index: Int, transaction: Transaction) {
        if (index in fullList.indices) {
            fullList[index] = transaction
            filter("ALL", "")
        }
    }

    fun deleteTransaction(index: Int) {
        if (index in fullList.indices) {
            fullList.removeAt(index)
            filter("ALL", "")
        }
    }

    fun filter(type: String, query: String) {
        filteredList = fullList.filter {
            val matchesType = when (type) {
                "INCOME" -> it.isIncome
                "EXPENSE" -> !it.isIncome
                else -> true
            }

            val matchesSearch = it.title.contains(query, true) ||
                    it.category.contains(query, true)

            matchesType && matchesSearch
        }.toMutableList()

        notifyDataSetChanged()
    }

    fun getAllTransactions(): MutableList<Transaction> = fullList
}