package com.example.myapplication.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.myapplication.modelclass.Users;
import com.example.myapplication.servicesinterface.UserServiceInterface;

import java.util.ArrayList;
import java.util.List;

public class usersDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "usersDB.db";
    public static final String TABLE_NAME = "Users";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";

    public usersDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+"( "+USER_ID+" INTEGER, "+ USER_NAME+" TEXT NOT NULL,"+PASSWORD+" TEXT NOT NULL"+")";
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + USER_NAME + " TEXT, " + PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }


    public void addHandler(Users users) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_NAME, users.getUserName());
        contentValues.put(PASSWORD, users.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<Users> loadHandler() {
        List<Users> usersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Users users = new Users();
            users.setUserId(cursor.getInt(0));
            users.setUserName(cursor.getString(1));
            usersList.add(users);


        }
        return usersList;
    }

    public Users findHndler(String username, String password) {

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+USER_NAME+" = "+"'"+username+"'"
                + " AND "+PASSWORD+" = "+"'"+password+"'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        Users users = new Users();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            users.setUserId(Integer.parseInt(cursor.getString(0)));
            users.setUserName(cursor.getString(1));
            cursor.close();

        }else{
            users = null;
        }
        sqLiteDatabase.close();

        return users;

    }
}



