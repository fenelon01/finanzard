package com.example.finanzard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {  // Ensure AppCompatActivity is being extended

    private lateinit var etDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etAmount: EditText
    private lateinit var btnSave: Button
    private lateinit var btnViewStats: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etDescription = findViewById(R.id.et_description)
        spinnerCategory = findViewById(R.id.spinner_category)
        etAmount = findViewById(R.id.et_amount)
        btnSave = findViewById(R.id.btn_save)
        btnViewStats = findViewById(R.id.btn_view_stats)

        btnSave.setOnClickListener {
            saveExpense()
        }

        btnViewStats.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveExpense() {
        val description = etDescription.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val amount = etAmount.text.toString().toDoubleOrNull()

        if (description.isNotEmpty() && amount != null) {
            dbHelper.insertExpense(description, category, amount)
            Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show()
            etDescription.text.clear()
            etAmount.text.clear()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
