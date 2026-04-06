package com.sejal.expensetracker.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String,
    val description : String,
    val amount: Double,
    val date: String,
    val type: String
) : Parcelable