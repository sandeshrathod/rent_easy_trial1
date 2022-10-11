package com.example.renteasytrial1st;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class admin_usersss extends AppCompatActivity {
    RecyclerView rcl;
    ArrayList<userbag> usr = new ArrayList<>();


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usersss);
        db = FirebaseFirestore.getInstance();
        rcl = findViewById(R.id.rccl);

        db.collection("users").addSnapshotListener((value, error) -> {
            usr.clear();
            for (DocumentSnapshot snapshot : value) {
                usr.add(new userbag(snapshot.getString("fName"), snapshot.getString("email")));
            }
            useradaptr v=new useradaptr(usr,this);
            rcl.setAdapter(v);
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcl.setLayoutManager(linearLayoutManager);


    }
}