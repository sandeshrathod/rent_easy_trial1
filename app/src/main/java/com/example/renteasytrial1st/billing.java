package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class billing extends AppCompatActivity {
    private static final String TAG = "billing";
TextView rentvt,depositvt,totalvt;
EditText pr_day;
Button btn_pay;
int in,ine;
String day;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String pname = "pname";
    private static final String deposit = "deposit";
    private static final String Rent = "Rent";
    String pid;
    private static final String description = "description";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        rentvt = findViewById(R.id.renttv);
        pr_day= findViewById(R.id.num_day);

        depositvt = findViewById(R.id.deposittv);
       totalvt= findViewById(R.id.totaltv);
        btn_pay= findViewById(R.id.pay_btn);
        btn_pay.setOnClickListener(view -> paybil()
        );
Intent intent= getIntent();
pid= intent.getStringExtra("pid");

        DocumentReference docRef = db.collection("product").document(pid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String title = document.getString(pname);
                        String rent= document.getString(Rent);
                        String depo = document.getString(deposit);
                        String desc = document.getString(description);
                        Toast.makeText(billing.this, pname, Toast.LENGTH_SHORT).show();//Map<String, Object> note = documentSnapshot.getData();
              /*          String uri =document.getString(image);
                        Glide.with(getApplicationContext()).load(uri).into(imageView);*/
                        rentvt.setText(rent);
                        depositvt.setText(depo);
                        in = Integer.valueOf(rentvt.getText().toString());


                        ine = Integer.valueOf(depositvt.getText().toString());
/*
                        int inee = in +ine;

                        String tmp = String.valueOf(inee);
                        totalvt.setText(tmp);*/

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }

        });
    }

    private void paybil() {
    int innn= Integer.parseInt(pr_day.getText().toString());
int val=innn*in  ;
        int inee = val +ine;

        String tmp = String.valueOf(inee);
        totalvt.setText(tmp);
    }
}