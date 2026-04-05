package com.codewithfk.expensetracker.android.feature.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.expensetracker.android.base.BaseViewModel
import com.codewithfk.expensetracker.android.base.HomeNavigationEvent
import com.codewithfk.expensetracker.android.base.UiEvent
import com.codewithfk.expensetracker.android.utils.Utils
import com.codewithfk.expensetracker.android.data.dao.ExpenseDao
import com.codewithfk.expensetracker.android.data.model.ExpenseEntity
import com.codewithfk.expensetracker.android.data.model.ExpenseSummary
import com.codewithfk.expensetracker.android.feature.home.HomeUiEvent
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(val dao: ExpenseDao) : BaseViewModel() {
    val entries = dao.getAllExpenseByDate()
    val topEntries = dao.getTopExpenses()


    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry> {
        val list = mutableListOf<Entry>()
        for (entry in entries) {
            val formattedDate = Utils.getMillisFromDate(entry.date)
            list.add(Entry(formattedDate.toFloat(), entry.total_amount.toFloat()))
        }
        return list
    }

    override fun onEvent(event: UiEvent) {

        when(event){
            is StatsUiEvent.OnEditClicked->{
                viewModelScope.launch {

                    if(event.item.type == "Income"){
                        _navigationEvent.emit(
                            HomeNavigationEvent.NavigateToAddIncome(event.item)
                        )
                    }else{
                        _navigationEvent.emit(
                            HomeNavigationEvent.NavigateToAddExpense(event.item)
                        )
                    }

                }
            }
            is StatsUiEvent.OnDeleteClicked->{
                viewModelScope.launch {
                    dao.deleteExpense(event.item)
                }

            }


        }

    }
}

sealed class StatsUiEvent : UiEvent(){


    data class OnDeleteClicked(val item : ExpenseEntity ) : StatsUiEvent()
    data class OnEditClicked(val item : ExpenseEntity) : StatsUiEvent()
}


