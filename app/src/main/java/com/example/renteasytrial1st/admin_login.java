package com.example.renteasytrial1st;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_login extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    EditText Emailid, Password;
    Button Buttonlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Buttonlogin = findViewById(R.id.lgn_btnAD);
        firebaseAuth= FirebaseAuth.getInstance();
        Emailid = findViewById(R.id.fed22);
        Password = findViewById(R.id.pass22);

        Buttonlogin.setOnClickListener(view -> SignIn());
    }

    public void SignIn() {

        String emailId = Emailid.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(emailId)) {
            Emailid.setError("Please Enter EmailId");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Password.setError("Please Enter Password");

            return;
        }


        firebaseAuth.signInWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(admin_login.this, task -> {
//                        progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        if (emailId.equals("admin@gmail.com")) {
                            startActivity(new Intent(this, Admin_homepage.class));
                        } else {
                            FirebaseAuth.getInstance().signOut();
                        }
                    } else {
                        Toast.makeText(this, "Login failed" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}