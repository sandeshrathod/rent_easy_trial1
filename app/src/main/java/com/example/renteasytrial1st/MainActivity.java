package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button kk,sgn_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kk =findViewById(R.id.lgn_btn);
        kk.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,login.class));
        });
        sgn_p = findViewById(R.id.sign_p);
        sgn_p.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,homepage.class));
        });

    }
}