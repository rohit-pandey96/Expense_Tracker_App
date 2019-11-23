package com.example.myapplication.servicesimpl;

import android.content.Context;

import com.example.myapplication.modelclass.Users;
import com.example.myapplication.servicesinterface.UserServiceInterface;
import com.example.myapplication.tables.usersDBHandler;

public class UserServiceImpl implements UserServiceInterface {


    @Override
    public Users login(String username, String password,Context context) {
        return new usersDBHandler(context,null,null,1).findHndler(username,password);


    }

    public void signup(Users user, Context context){
        new usersDBHandler(context,null,null,1).addHandler(user);



    }

}
