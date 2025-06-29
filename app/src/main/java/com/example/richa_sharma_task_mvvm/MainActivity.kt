package com.example.richa_sharma_task_mvvm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.richa_sharma_task_mvvm.databinding.ActivityMainBinding
import com.example.richa_sharma_task_mvvm.presentation.HoldingsUiState
import com.example.richa_sharma_task_mvvm.presentation.ui.adapter.HoldingsAdapter
import com.example.richa_sharma_task_mvvm.presentation.viewmodel.HoldingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HoldingsViewModel by viewModels()
    private lateinit var holdingsAdapter: HoldingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        holdingsAdapter = HoldingsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = holdingsAdapter
        binding.summaryHeader.setOnClickListener {
            toggleSummary()
        }
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is HoldingsUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.summaryCard.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                    }

                    is HoldingsUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.summaryCard.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.VISIBLE

                        binding.tvCurrentValue.text =
                            getString(R.string.current_value_format, state.summary.currentValue)
                        binding.tvTotalInvestment.text =
                            getString(
                                R.string.total_investment_format,
                                state.summary.totalInvestment
                            )
                        binding.tvTotalPnL.text =
                            getString(R.string.total_pnl_format, state.summary.totalPnL)
                        binding.tvTodaysPnL.text =
                            getString(R.string.todays_pnl_format, state.summary.todayPnLValue)
                        holdingsAdapter.setData(state.holdings)
                    }

                    is HoldingsUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.summaryCard.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun toggleSummary() {
        val content = binding.summaryContent
        val icon = binding.ivExpandIcon
        if (content.isVisible) {
            content.visibility = View.GONE
            icon.setImageResource(R.drawable.ic_arrow_down)
        } else {
            content.visibility = View.VISIBLE
            icon.setImageResource(R.drawable.ic_arrow_up)
        }
    }

}
