package com.example.renteasytrial1st;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class registration extends AppCompatActivity {

    String EmailIdPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

   private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    public static final String TAG = "TAG";
    String userID;
    Button btn_regstr;
    EditText et_name, et_email, et_phno,et_addrs,et_pass,et_cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btn_regstr = findViewById(R.id.btn_reg);

        et_name = findViewById(R.id.et_name_reg);
        et_email = findViewById(R.id.et_email_reg);
        et_phno = findViewById(R.id.et_phno_reg);
        et_addrs = findViewById(R.id.et_addrs_reg);
        et_pass = findViewById(R.id.et_pass_reg);
        et_cpass = findViewById(R.id.et_cpass_reg);
     firebaseAuth = FirebaseAuth.getInstance();
     db = FirebaseFirestore.getInstance();

/*
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/
        btn_regstr.setOnClickListener(view -> register());

    }
    public void register(){
        Toast.makeText(this, "ho rha hia", Toast.LENGTH_SHORT).show();
        String Name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String Phone = et_phno.getText().toString().trim();
        String Address = et_addrs.getText().toString().trim();
        String password = et_pass.getText().toString().trim();
        String Cpassword = et_cpass.getText().toString().trim();

        if (TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();
            et_name.setError("Please Enter UserName");
            return;
        }
        else if (TextUtils.isEmpty(email)){
            et_email.setError("Please Enter EmailId");
            return;
        }
        else if (!email.matches(EmailIdPattern)){
            et_email.setError("Please enter valid EmailId");
            return;
        }
        else if (TextUtils.isEmpty(password)){
            et_pass.setError("Please Enter Password");
            return;
        }
        else if (TextUtils.isEmpty(Cpassword)){
            et_cpass.setError("Please Re-Enter Password");
            return;
        }
        else if (password.length()<6){
            et_pass.setError("Password is too week");
            return;
        }
        if (password.equals(Cpassword)){

        Log.e(TAG,"error");
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    // send verification link

                 /*   FirebaseUser fuser =firebaseAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(registration.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });*/

                    Toast.makeText(registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                    userID = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",Name);
                    user.put("email",email);
                    user.put("address",Address);
                    user.put("phone",Phone);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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

                }else {
                    Toast.makeText(registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });





    }
}
}
