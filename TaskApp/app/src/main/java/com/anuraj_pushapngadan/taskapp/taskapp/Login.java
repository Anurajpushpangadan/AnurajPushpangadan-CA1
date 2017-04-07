package com.anuraj_pushapngadan.taskapp.taskapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button LoginBtn;
    EditText user, pass;

    public static String UserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);

        LoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Login(user.getText().toString(),pass.getText().toString());

            }
        });

    }


    private void Login(String user, String pass){
        String userName1 = "admin";
        String password1 = "admin123";

        String userName2 = "user";
        String password2 = "user123";
        UserName = user;
        if(user.equals(userName1) && pass.equals(password1)){
            Intent intent = new Intent(this, AddTask.class);
            startActivity(intent);
        }
        else if(user.equals(userName2) && pass.equals(password2)){
            Intent intent = new Intent(this, AddTask.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "User name or Password is not valid!", Toast.LENGTH_SHORT).show();
        }

    }


}
