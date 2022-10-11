package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myordr extends AppCompatActivity {
TextView rreqstp,myordr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myordr);
        rreqstp = findViewById(R.id.rqstdpro);
        myordr = findViewById(R.id.MyOrders);
       myordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myordr.this,my_orders.class);
                startActivity(intent);
            }
        });
        rreqstp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myordr.this,req_pro.class);
                startActivity(intent);
            }
        });

    }
}