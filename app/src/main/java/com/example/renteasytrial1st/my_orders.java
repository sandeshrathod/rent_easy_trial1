package com.example.renteasytrial1st;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class my_orders extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ordr_prod> userArrayList = new ArrayList<>();
    Myordradapter myAdapter;
    FirebaseFirestore db;
    Button btt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView = findViewById(R.id.recylervieew);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        btt = findViewById(R.id.btn_reeg);
        userArrayList = new ArrayList<ordr_prod>();
        myAdapter = new Myordradapter(my_orders.this, userArrayList);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView.setAdapter(myAdapter);


        /*     String Loca = (String)floca_spn.getSelectedItem();*/
        String uid = firebaseAuth.getCurrentUser().getUid();

        db.collection("product").whereEqualTo("rqst status","1").whereEqualTo("userid",uid)
                .addSnapshotListener((value, error) -> {
                    userArrayList.clear();
                    assert value != null;
                    for (DocumentSnapshot snapshot : value) {
                        userArrayList.add(new ordr_prod(
                                snapshot.getId(),
                                snapshot.getString("pname"),
                                snapshot.getString("deposit"),
                                snapshot.getString("Rent"),
                                snapshot.getString("reqstduid")

                        ));
//                        Toast.makeText(this, "id: " + snapshot.getString("pname"), Toast.LENGTH_SHORT).show();
                    }
                    Myordradapter myAdaptr = new Myordradapter(this, userArrayList);
                    recyclerView.setAdapter(myAdaptr);
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);




/*
        db.collection("product")
                .whereEqualTo("rqst status", "1")
                .whereEqualTo("userid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Toast.makeText(my_orders.this, "bas yaha tak aya", Toast.LENGTH_SHORT).show();

                        if (error != null){


                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                userArrayList.add(dc.getDocument().toObject(ordr_prod.class));

                            }

                            myAdapter.notifyDataSetChanged();

                        }

                    }
                });*/


    }
}