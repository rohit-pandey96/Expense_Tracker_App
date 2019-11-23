package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    TextView launchtxt;
//    private EditText userId, userName, passwword;
//    private Button loginbtn;
//    private Users users;
//    TextView signup;
//    View view1;
//    UserServiceInterface userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//hide the title
        getSupportActionBar().hide();//hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//enable full screen
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);

                finish(); //This closes current activity
            }
        }, 3000); //It means 4 seconds
    }
//        view1 = findViewById(R.id.loginview);
//        userId = findViewById(R.id.loginUser);
//        passwword = findViewById(R.id.loginPassword);
//        loginbtn = view1.findViewById(R.id.loginuserbtn);
//        signup = (TextView) view1.findViewById(R.id.signuptxt);
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplication(),Signup.class));
//            }
//        });
//
//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userService = new UserServiceImpl();
//                if(userService.login(userId.getText().toString(),passwword.getText().toString(),getApplicationContext())!=null){
//                    startActivity(new Intent(getApplicationContext(),Home.class));
//
//                }else{
//                    Toast.makeText(getApplicationContext(),"Enter valid credentials!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



//        loginbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                users.setUserId(Integer.parseInt(userId.getText().toString()));
//                users.setUserName();
//            }
//        });

//    public void adduser(){
//        startActivity(new Intent(this, Signup.class));
//
//    }
}

