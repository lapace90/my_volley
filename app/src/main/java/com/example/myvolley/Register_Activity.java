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

import java.util.Queue;

public class Register_Activity extends AppCompatActivity {

    private EditText email, registerId, registerPassword1, registerPassword2;
    private Button registerBtn;
    private RequestQueue queue;
    private MyRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    email = findViewById(R.id.email);
    registerId = findViewById(R.id.registerId);
    registerPassword1 = findViewById(R.id.registerPassword1);
    registerPassword2 = findViewById(R.id.registerPassword2);
    registerBtn = findViewById(R.id.registerBtn);

    queue = MySingleton.getInstance(this).getRequestQueue();
    request = new MyRequest(this, queue);

    registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final String LOGIN = registerId.getText().toString().trim();
            final String EMAIL = email.getText().toString().trim();
            final String PASSWORD = registerPassword1.getText().toString().trim();
            final String PASSWORD2 = registerPassword2.getText().toString().trim();

            request.register(LOGIN, EMAIL, PASSWORD, PASSWORD2, new MyRequest.RetoursPHP() {
                @Override
                public void toutOk(String message) {
                    Log.d("PHP", "messagesPHP" + message);
                    Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                    Toast.makeText(Register_Activity.this, "" + message, Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                }

                @Override
                public void pasOk(String message) {
                    Log.d("PHP", "passage dans PAS OK de RegisterActivity");
                    Toast.makeText(Register_Activity.this,"Attention" + message, Toast.LENGTH_SHORT).show();
                    return;
                }

                @Override
                public void systemError(String message) {
                    Toast.makeText(Register_Activity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    });



    }
}