package com.example.myapplication.servicesimpl;

import android.content.Context;

import com.example.myapplication.modelclass.Expense;
import com.example.myapplication.servicesinterface.ExpenseInterface;
import com.example.myapplication.tables.expenseDBHandler;

import java.util.List;

public class ExpenseImpl implements ExpenseInterface {


    @Override
    public void addExpense(Expense expense,Context context) {
        new expenseDBHandler(context,null,null,1).addHandler(expense);


    }

    @Override
    public List<Expense> getList(Context context) {
        return new expenseDBHandler(context,null,null,3).loadHandler();
    }

    @Override
    public void deleteExpense(int id,Context context) {
        new expenseDBHandler(context,null,null,3).deleteHandler(id);
    }
}
