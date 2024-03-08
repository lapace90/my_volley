package com.example.myvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

public class Login_Activity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button loginBtn;
    private Button registerBtn;
    private RequestQueue queue;
    private MyRequest request;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String LOGIN = login.getText().toString().trim();
                final String PASSWORD = password.getText().toString().trim();

                request.login(LOGIN, PASSWORD, new MyRequest.RetoursPHP() {
                    @Override
                    public void toutOk(String message) {
                        Toast.makeText(Login_Activity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void pasOk(String message) {
                        Toast.makeText(Login_Activity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void systemError(String message) {
                        Toast.makeText(Login_Activity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(myIntent);

            }
        });



    }
}