package com.example.richa_sharma_task_mvvm.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.richa_sharma_task_mvvm.R
import com.example.richa_sharma_task_mvvm.data.model.Holding
import com.example.richa_sharma_task_mvvm.databinding.ItemHoldingBinding

class HoldingsAdapter : RecyclerView.Adapter<HoldingsAdapter.HoldingViewHolder>() {

    private val holdings = mutableListOf<Holding>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newHoldings: List<Holding>) {
        holdings.clear()
        holdings.addAll(newHoldings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val binding = ItemHoldingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HoldingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        holder.bind(holdings[position])
    }

    override fun getItemCount(): Int = holdings.size

    inner class HoldingViewHolder(private val binding: ItemHoldingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(holding: Holding) = with(binding) {
            tvStockName.text = holding.symbol
            val ctx = root.context
            tvLtpLabel.text = ctx.getString(R.string.str_ltp)
            tvLtpValue.text = ctx.getString(R.string.currency_value, holding.ltp)

            tvQuantityLabel.text = ctx.getString(R.string.str_qty)
            tvQuantityValue.text = holding.quantity.toString()

            val pnl = (holding.ltp - holding.averagePrice) * holding.quantity
            tvPnLLabel.text = ctx.getString(R.string.str_pnl)
            tvPnLValue.text = ctx.getString(R.string.currency_value, pnl)

            val pnlColor = if (pnl >= 0) {
                ctx.getColor(android.R.color.holo_green_dark)
            } else {
                ctx.getColor(android.R.color.holo_red_dark)
            }
            tvPnLValue.setTextColor(pnlColor)
        }
    }

}

