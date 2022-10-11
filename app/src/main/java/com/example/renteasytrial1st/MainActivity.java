package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button kk,sgn_p,temp;

    FirebaseAuth fAuth;
    FirebaseUser Fuser;

TextView he;
   /* @Override
    protected void onStart() {
        Fuser= fAuth.getCurrentUser();
        if(Fuser!=null)
        {
            startActivity(new Intent(this,homepage.class));
        }
        super.onStart();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        kk =findViewById(R.id.lgn_btn);
    /*    temp.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,feedback.class));
        });*/   /* temp =findViewById(R.id.button2);*/

        kk.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,login.class));
        });
        sgn_p = findViewById(R.id.sign_p);
        sgn_p.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,registration.class));
        });
        he = findViewById(R.id.how_it);
        he.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,admin_login.class));
        });


    }
}