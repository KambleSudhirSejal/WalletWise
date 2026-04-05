package com.codewithfk.expensetracker.android.base

import com.codewithfk.expensetracker.android.data.model.ExpenseEntity

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