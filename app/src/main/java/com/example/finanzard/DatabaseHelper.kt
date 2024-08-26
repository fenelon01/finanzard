package com.example.finanzard

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "finanzard.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "expenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {    val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_DESCRIPTION TEXT, $COLUMN_CATEGORY TEXT, $COLUMN_AMOUNT REAL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(description: String, category: String, amount: Double) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DESCRIPTION, description)
        values.put(COLUMN_CATEGORY, category)
        values.put(COLUMN_AMOUNT, amount)
        db.insert(TABLE_NAME, null, values)
    }

    fun getTotalExpense(): Double {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_NAME", null)
        var totalExpense = 0.0
        if (cursor.moveToFirst()) {
            totalExpense = cursor.getDouble(0)
        }
        cursor.close()
        return totalExpense
    }

    fun getExpenseByCategory(): String {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_CATEGORY, SUM($COLUMN_AMOUNT) FROM $TABLE_NAME GROUP BY $COLUMN_CATEGORY", null)
        val expenseByCategory = StringBuilder()
        while (cursor.moveToNext()) {
            val category = cursor.getString(0)
            val amount = cursor.getDouble(1)
            expenseByCategory.append("$category: $amount\n")
        }
        cursor.close()
        return expenseByCategory.toString()
    }}

