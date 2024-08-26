package com.example.finanzard

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StatisticsActivity : AppCompatActivity() {

    private lateinit var tvTotalExpense: TextView
    private lateinit var tvExpenseByCategory: TextView
    private lateinit var btnBack: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        dbHelper = DatabaseHelper(this)

        tvTotalExpense = findViewById(R.id.tv_total_expense)
        tvExpenseByCategory = findViewById(R.id.tv_expense_by_category)
        btnBack = findViewById(R.id.btn_back)

        displayStatistics()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun displayStatistics() {
        val totalExpense = dbHelper.getTotalExpense()
        val expenseByCategory = dbHelper.getExpenseByCategory()

        tvTotalExpense.text = "Total Expenses: $totalExpense"
        tvExpenseByCategory.text = "Expenses by Category:\n$expenseByCategory"
    }
}
