package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class edit_profile extends AppCompatActivity {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final String TAG = "EditProfileActivityInfo";
    String checkedName;

    String userID;

    Uri uri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db ;

    DocumentReference documentReference;





    ImageView profilePicture;
    TextInputEditText fullName, email, phoneNumber, address;

    Button buttonCancel, buttonSave;
    ImageButton buttonChangePicture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);   firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();




        profilePicture = findViewById(R.id.profilePicture);

        fullName = findViewById(R.id.textInputLayout_FullName);

        phoneNumber = findViewById(R.id.textInputLayout_Phone);
       address = findViewById(R.id.addrss_et);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSave = findViewById(R.id.buttonSave);
        buttonChangePicture = findViewById(R.id.buttonChangePicture);




        buttonCancel.setOnClickListener(view -> finish());
        buttonSave.setOnClickListener(view -> UpdatePersonalInformation());
        buttonChangePicture.setOnClickListener(view -> ChangePicture());




        if (firebaseUser!=null) {
            LoginUserInfo();
        }

        PersonalInformation();


    }

    private void LoginUserInfo() {
        String userID = Objects.requireNonNull(firebaseUser).getUid();



        documentReference = db.collection("users").document(userID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){


                Glide.with(getApplicationContext()).load(value.getString("imageUrl"))
                        .placeholder(R.drawable.ic_person_png).into(profilePicture);
            }
        });
    }

    private void UpdatePersonalInformation() {

        String userID = Objects.requireNonNull(firebaseUser).getUid();





        String FullName = Objects.requireNonNull(fullName.getText()).toString();
//        String EmailID = email.getText().toString();
        String PhoneNumber = Objects.requireNonNull(phoneNumber.getText()).toString();
        String Address = Objects.requireNonNull(address.getText()).toString();


    /*    if (FullName.equals("")){
            Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (PhoneNumber.equals("")){
            Toast.makeText(this, "Please Enter your Phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Address.equals("")){
            Toast.makeText(this, "Please Enter your Date of Birth", Toast.LENGTH_SHORT).show();
            return;
        }*/


        db.collection("users").document(userID).update(
                "fName", FullName,
                "phone", PhoneNumber,
                "address", Address


        ).addOnSuccessListener(unused ->
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), "Failed to create link" + e,
                                Toast.LENGTH_SHORT).show());

    }



    private void setImageURL(String uri) {
        String userID = Objects.requireNonNull(firebaseUser).getUid();

        db.collection("users").document(userID).update(
                "imageUrl", uri
        ).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "link created", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to create link" + e, Toast.LENGTH_SHORT).show());

        Log.i(TAG, "Saved link: " + uri);
    }
    private void PersonalInformation() {



        String userID = Objects.requireNonNull(firebaseUser).getUid();

        documentReference = db.collection("users").document(userID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){
               /* fullName.setText(value.getString("fName"));
                email.setText(value.getString("email"));
                phoneNumber.setText(value.getString("phone"));*/
             /*   address.setText(value.getString("address"));*/




            }
        });

    }

    private void ChangePicture(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Choose Image"),IMAGE_REQUEST_ID);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);

                storageReference = storageReference.child("Profile Pictures/" +userID);
                storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> setImageURL(String.valueOf(uri)));

                    Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}