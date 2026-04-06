package com.sejal.expensetracker.android.base

import com.sejal.expensetracker.android.data.model.ExpenseEntity

sealed class NavigationEvent {
    object NavigateBack : NavigationEvent()
}

sealed class AddExpenseNavigationEvent : NavigationEvent() {
    object MenuOpenedClicked : AddExpenseNavigationEvent()
}

sealed class HomeNavigationEvent : NavigationEvent() {
    data class NavigateToAddExpense(val item : ExpenseEntity?) : HomeNavigationEvent()
    data class NavigateToAddIncome(val item : ExpenseEntity?) : HomeNavigationEvent()
    object NavigateToSeeAll : HomeNavigationEvent()
}