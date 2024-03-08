package com.example.myvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class LoginOkActivity extends AppCompatActivity {
private EditText textViewPseudo, textViewEmail, textViewEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ok);

        textViewPseudo = findViewById(R.id.textViewPseudo);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewEdit = findViewById(R.id.textViewEdit);


    }
}