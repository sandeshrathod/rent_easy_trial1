package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class user_desc extends AppCompatActivity {
TextView uname, uphon;
    ImageView uimg;
    Button appr_btn;
    private static final String TAG = "prod_desc";
    private static final String phn = "phone";
    private static final String nm = "fname";
   private static final String image = "imageUrl";
   String ppid;
String rsuid;
Button btn;
    private FirebaseStorage storage;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_desc);
        uname = findViewById(R.id.username);
        uphon = findViewById(R.id.phone);
        appr_btn= findViewById(R.id.ap_btn);
        uimg = findViewById(R.id.uimage);
        Intent startingIntent = getIntent();
        appr_btn.setOnClickListener(view -> approveRequest());
        ppid = startingIntent.getStringExtra("pid");
      /*  Toast.makeText(this, rid, Toast.LENGTH_SHORT).show();
      */  storage = FirebaseStorage.getInstance();
      db.collection("product").document(ppid).get().addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
              DocumentSnapshot document = task.getResult();
              if (document.exists()){

                  rsuid = document.getString("reqstduid");

              }
              else {
                  Log.d(TAG, "no Such document");
              }

          }
          else{
              Log.d(TAG,"err:" + task.getException());
          }
      });
       Handler handler = new Handler();


        final Runnable r = new Runnable() {
            public void run() {

                DocumentReference docRef = db.collection("users").document(rsuid);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(user_desc.this, "hogygwfuqgyh", Toast.LENGTH_SHORT).show();
                                String title = document.getString("fName");
                                String phon = document.getString("phone"); //Map<String, Object> note = documentSnapshot.getData();
                                String uri =document.getString(image);
                                Glide.with(getApplicationContext()).load(uri).into(uimg);
                                Toast.makeText(user_desc.this, phon, Toast.LENGTH_SHORT).show();
                                uname.setText(title);
                                uphon.setText(phon);
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }

                });
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
   /*   db.collection("users").document(rsuid).get().addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
              DocumentSnapshot document = task.getResult();
              if (document.exists()){

              }
              else {
                  Log.d(TAG, "no Such document");
              }

          }
          else{
              Log.d(TAG,"err:" + task.getException());
          }
      });*/

/*


*/
      /*  Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();*/
    }

    private void approveRequest() {

        db.collection("product").document(ppid).update("approvalRequest", "Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(user_desc.this, "request Approved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "failed"+e, Toast.LENGTH_SHORT).show();
        });
    }
}