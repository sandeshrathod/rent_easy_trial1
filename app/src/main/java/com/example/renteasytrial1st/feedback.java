package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class feedback extends AppCompatActivity {
Spinner spn1,spn2,spn3;
EditText fd1,fd2,fd3;
String fdd1,fdd2,fdd3,spp1,spp2,spp3;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    public static final String TAG = "TAG";
Button btnsub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        fd1 = findViewById(R.id.fed22);
        fd2 = findViewById(R.id.fed22);
        fd3 = findViewById(R.id.fed_et3);

        spn1 = findViewById(R.id.rt_spn1);
        spn2 = findViewById(R.id.rt_spn2);
        spn3 = findViewById(R.id.rt_spn3);

        db = FirebaseFirestore.getInstance();
      btnsub =findViewById(R.id.fd_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        List<String> rating1 = new ArrayList<String>();
        rating1.add("1 star");
        rating1.add("2 star");
        rating1.add("3 star ");
        rating1.add("4 star");
        rating1.add("5 star");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rating1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(adapter1);

        List<String> rating2 = new ArrayList<String>();
        rating2.add("1 star");
        rating2.add("2 star");
        rating2.add("3 star ");
        rating2.add("4 star");
        rating2.add("5 star");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rating2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn2.setAdapter(adapter2);
        List<String> rating3 = new ArrayList<String>();
        rating3.add("1 star");
        rating3.add("2 star");
        rating3.add("3 star ");
        rating3.add("4 star");
        rating3.add("5 star");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rating3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn3.setAdapter(adapter3);

    }
    private void SaveProductInfoToDatabase()
    {
        fdd1= fd1.getText().toString();
        fdd2= fd2.getText().toString();
        fdd3= fd3.getText().toString();
        spp1 = (String)spn1.getSelectedItem();
        spp2 = (String)spn2.getSelectedItem();
        spp3 = (String)spn3.getSelectedItem();

        //DocumentReference documentReference = db.collection("product").document( productRandomKey);

        String userID = firebaseAuth.getCurrentUser().getUid();
        Map<String,Object> feedbacks = new HashMap<>();

        feedbacks.put("userid", userID);
        feedbacks.put("1st Q",fdd1);
        feedbacks.put("2nd Q",fdd2);
        feedbacks.put("3rd Q",fdd3);
        feedbacks.put("rating1", spp1);
        feedbacks.put("rating2", spp2);
        feedbacks.put("rating3", spp3);


        db.collection("feedback").document(userID).set(feedbacks).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
        startActivity(new Intent(getApplicationContext(),MainActivity.class));


    }

}