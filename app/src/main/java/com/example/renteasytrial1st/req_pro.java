package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class req_pro extends AppCompatActivity {
    RecyclerView recyclr;
FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    FirebaseUser fuser;
ArrayList<rqstpr> arrlist=new ArrayList<>();
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_pro);
        recyclr = findViewById(R.id.recycler2);
        db= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
 fuser = firebaseAuth.getCurrentUser();
 uid=fuser.getUid();
        recyclr.setHasFixedSize(false);
        recyclr.setLayoutManager(new LinearLayoutManager(this));
        db.collection("product").whereEqualTo("approvalRequest","Approved").whereEqualTo("reqstduid",uid)
                .addSnapshotListener((value, error) -> {

                    arrlist.clear();
                    assert value != null;
                    for (DocumentSnapshot snapshot : value) {
                        Toast.makeText(this, "size", Toast.LENGTH_SHORT).show();
                        arrlist.add(new rqstpr(

                                snapshot.getId(),
                                snapshot.getString("pname"),
                                snapshot.getString("deposit"),
                                snapshot.getString("Rent")


                        ));
//                        Toast.makeText(this, "id: " + snapshot.getString("pname"), Toast.LENGTH_SHORT).show();
                    }
                    rqstadpter myAdaptr = new rqstadpter( arrlist,this);
                   recyclr.setAdapter(myAdaptr);
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclr.setLayoutManager(linearLayoutManager);

    }
}