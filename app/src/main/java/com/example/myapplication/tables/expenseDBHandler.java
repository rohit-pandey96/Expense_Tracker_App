package com.example.myapplication.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.myapplication.modelclass.Expense;

import java.util.ArrayList;
import java.util.List;

public class expenseDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ExpensesDB.db";
    public static final String TABLE_NAME = "Expenses";
    public static final String ID = "expense_id";
    public static final String PLACE = "place";
    public static final String DATE = "date";
    public static final String DAY = "day";
    public static final String TIME = "time";
    public static final String SPEND = "spend";
    public static final String EXPENSE_TYPE = "expense_type";


    public expenseDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+PLACE+" TEXT, "+DATE+" DATE, "+DAY+" TEXT, "+TIME+" TEXT, "+EXPENSE_TYPE+" TEXT, "+SPEND+" TEXT );";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addHandler(Expense expense){
        ContentValues contentValues = new ContentValues();
        contentValues.put("PLACE",expense.getPlace());
        contentValues.put("DATE",expense.getDate());
        contentValues.put("DAY",expense.getDay());
        contentValues.put("TIME",expense.getTime());
        contentValues.put("SPEND",expense.getSpend());
        contentValues.put("EXPENSE_TYPE",expense.getExpense_type());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();


    }
    public List<Expense> loadHandler(){
        List<Expense> list = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while(cursor.moveToNext()){
            Expense addExpense = new Expense();
            addExpense.setExpense_id(cursor.getInt(0));
            addExpense.setPlace(cursor.getString(1));
            addExpense.setDate(cursor.getString(2));
            addExpense.setDay(cursor.getString(3));
            addExpense.setTime(cursor.getString(4));
            addExpense.setExpense_type(cursor.getString(5));
            addExpense.setSpend(cursor.getString(6));
            list.add(addExpense);
        }
        return list;
    }
    public void deleteHandler(int id){
        String query ="SELECT * FROM "+ TABLE_NAME +" WHERE "+ ID +" = "+"'"+String.valueOf(id)+"'";

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        Expense expense = new Expense();
        if(cursor.moveToNext()){
            expense.setExpense_id(Integer.parseInt(cursor.getString(0)));
            sqLiteDatabase.delete(TABLE_NAME,ID +" =?",
                    new String[]{String.valueOf(expense.getExpense_id())});
            cursor.close();
        }
    }

}
