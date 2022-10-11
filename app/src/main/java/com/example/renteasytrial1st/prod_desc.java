package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class prod_desc extends AppCompatActivity {
    private static final String TAG = "prod_desc";
    private TextView PName,Pdesc;
    ImageView imageView;
    private FirebaseAuth firebaseAuth;
Button desc_btn;
    String str;
    private FirebaseStorage storage;
    private static final String image = "image";
    private static final String tname = "pname";
    private static final String tdesc = "description";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_desc);
        Intent startingIntent = getIntent();
        String pid = startingIntent.getStringExtra("pid");
        Toast.makeText(this, pid, Toast.LENGTH_SHORT).show();
        Pdesc = findViewById(R.id.pdesc);
        PName = findViewById(R.id.pname);
     desc_btn= findViewById(R.id.button);
       imageView = findViewById(R.id.imageView2);
        firebaseAuth = FirebaseAuth.getInstance();
      storage = FirebaseStorage.getInstance();
        DocumentReference docRef = db.collection("product").document(pid);


        desc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
           /*     String ses = pid;
                Intent intent = new Intent(getBaseContext(), billing.class);
                intent.putExtra("EXTRA_SESSION_ID", ses);

                startActivity(intent);*/
            /*    Toast.makeText(prod_desc.this, pid, Toast.LENGTH_SHORT).show();*/
                String userID = firebaseAuth.getCurrentUser().getUid();
                Map<String,Object> productMap = new HashMap<>();

                productMap.put("reqstduid", userID);
                productMap.put("rqst status", "1");

                db.collection("product")
                        .document(pid).update(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
        });




        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String title = document.getString(tname);
                         String descr = document.getString(tdesc); //Map<String, Object> note = documentSnapshot.getData();
                    String uri =document.getString(image);
                       Glide.with(getApplicationContext()).load(uri).into(imageView);
                        PName.setText(title);
                        Pdesc.setText(descr);
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
}