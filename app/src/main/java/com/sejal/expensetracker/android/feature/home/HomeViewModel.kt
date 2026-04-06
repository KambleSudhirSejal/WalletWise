package com.sejal.expensetracker.android.feature.home

import androidx.lifecycle.viewModelScope
import com.sejal.expensetracker.android.base.BaseViewModel
import com.sejal.expensetracker.android.base.HomeNavigationEvent
import com.sejal.expensetracker.android.base.UiEvent
import com.sejal.expensetracker.android.utils.Utils
import com.sejal.expensetracker.android.data.dao.ExpenseDao
import com.sejal.expensetracker.android.data.model.ExpenseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val dao: ExpenseDao) : BaseViewModel() {
    val expenses = dao.getAllExpense()

    override fun onEvent(event: UiEvent) {
        when (event) {
            is HomeUiEvent.OnAddExpenseClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(HomeNavigationEvent.NavigateToAddExpense(null))
                }
            }

            is HomeUiEvent.OnAddIncomeClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(HomeNavigationEvent.NavigateToAddIncome(null))
                }
            }

            is HomeUiEvent.OnSeeAllClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(HomeNavigationEvent.NavigateToSeeAll)
                }
            }

            is HomeUiEvent.OnDeleteClicked->{
                viewModelScope.launch {
                    dao.deleteExpense(event.item)
                }

            }
            is HomeUiEvent.OnEditClicked->{
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
        }
    }

    fun getBalance(list: List<ExpenseEntity>): String {
        var balance = 0.0
        for (expense in list) {
            if (expense.type == "Income") {
                balance += expense.amount
            } else {
                balance -= expense.amount
            }
        }
        return Utils.formatCurrency(balance)
    }

    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0
        for (expense in list) {
            if (expense.type != "Income") {
                total += expense.amount
            }
        }

        return Utils.formatCurrency(total)
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var totalIncome = 0.0
        for (expense in list) {
            if (expense.type == "Income") {
                totalIncome += expense.amount
            }
        }
        return Utils.formatCurrency(totalIncome)
    }
}

sealed class HomeUiEvent : UiEvent() {
    data object OnAddExpenseClicked : HomeUiEvent()
    data object OnAddIncomeClicked : HomeUiEvent()
    data object OnSeeAllClicked : HomeUiEvent()

    //NEW
    data class OnDeleteClicked(val item : ExpenseEntity ) : HomeUiEvent()
    data class OnEditClicked(val item : ExpenseEntity) : HomeUiEvent()
}
