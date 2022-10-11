package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Admin_homepage extends AppCompatActivity {
Button btnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);
        btnu = findViewById(R.id.users);
        btnu.setOnClickListener(view -> {
            startActivity(new Intent(this,admin_usersss.class));
        });
    }
}