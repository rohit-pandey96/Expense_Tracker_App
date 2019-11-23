package com.example.myapplication.servicesinterface;

import android.content.Context;

import com.example.myapplication.modelclass.Expense;

import java.util.List;

public interface ExpenseInterface {
    public void addExpense(Expense expense, Context context);
    public List<Expense> getList(Context context);
    public void deleteExpense(int id,Context context);
}
