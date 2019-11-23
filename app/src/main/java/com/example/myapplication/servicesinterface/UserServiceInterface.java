package com.example.myapplication.servicesinterface;

import android.content.Context;

import com.example.myapplication.modelclass.Users;

public interface UserServiceInterface {
    public Users login(String username,String password,Context context);
    public void signup(Users user, Context context);
}
