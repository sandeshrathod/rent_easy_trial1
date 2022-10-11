package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pro_reg extends AppCompatActivity {
Spinner catgry_spn,perid_spn,loc_spn ;
EditText name,desc,mfgdate,depo,rent;

    private String Ctgry,Desc,Rent, Mfg,Depo,Period,Loca, Pname, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl;
ImageView pro_img;
    public static final String TAG = "TAG";
    private FirebaseFirestore db;
    private StorageReference ProductImagesRef;
    private Uri ImageUri;
    private static final int GalleryPick = 1;
    private FirebaseAuth firebaseAuth;
Button btnn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_reg);
        catgry_spn = findViewById(R.id.catgry_spinn);
        perid_spn = findViewById(R.id.time_spinn);
        loc_spn = findViewById(R.id.loc_spinn);
        btnn = findViewById(R.id.proreg_btn);
        name = findViewById(R.id.et_pro_name);
        desc = findViewById(R.id.et_pro_desc);
        depo = findViewById(R.id.et_pro_deposit);
        rent = findViewById(R.id.et_pro_rent);
        mfgdate = findViewById(R.id.promfg_date);
        db = FirebaseFirestore.getInstance();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        firebaseAuth = FirebaseAuth.getInstance();

        pro_img = findViewById(R.id.pro_img);
        pro_img.setOnClickListener(view -> OpenGallery());
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Electronics");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catgry_spn.setAdapter(adapter);

        List<String> period= new ArrayList<String>();
        period.add("day");
        period.add("hour");
        period.add("week");
        period.add("month");

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, period);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        perid_spn.setAdapter(ada);

        List<String> location = new ArrayList<String>();
        location.add("pune");
        location.add("Jalgaon");
        location.add("Mumbai");
        location.add("Delhi");
        location.add("Bengluru");
        location.add("Hydrabad");
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loc_spn.setAdapter(adap);


        btnn.setOnClickListener(view -> validateuser());



    }
    public void  OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            pro_img.setImageURI(ImageUri);
        }

    }
    public void validateuser(){
        Ctgry= (String)catgry_spn.getSelectedItem();
        Loca = (String)loc_spn.getSelectedItem();
        Period = (String)perid_spn.getSelectedItem();
        Desc = desc.getText().toString();
        Mfg = mfgdate.getText().toString();
        Depo= depo.getText().toString();
        Rent = rent.getText().toString();
        Pname = name.getText().toString();
        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Desc))
        {
            Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Rent))
        {
            Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            regesterr();
        }
    }
    public void regesterr(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(pro_reg.this, "Error: " + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(pro_reg.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();


                            Toast.makeText(pro_reg.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();


                       SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

       /* String textu = (String)catgry_spn.getSelectedItem();
        Toast.makeText(this, "Selected: " + textu, Toast.LENGTH_LONG).show();
        String gndr = (String)perid_spn.getSelectedItem();
        Toast.makeText(this, "Selected: " + gndr, Toast.LENGTH_LONG).show();
        String loca = (String)loc_spn.getSelectedItem();
        Toast.makeText(this, "Selected: " + loca, Toast.LENGTH_LONG).show();*/

    }
    private void SaveProductInfoToDatabase()
    {
        //DocumentReference documentReference = db.collection("product").document( productRandomKey);

        String userID = firebaseAuth.getCurrentUser().getUid();
        Map<String,Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("userid", userID);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);

        productMap.put("image", downloadImageUrl);
        productMap.put("reqstduid", "");
        productMap.put("rqst status", "0");
        productMap.put("admincntrl", "0");
        productMap.put("pname", Pname);
        productMap.put("deposit", Depo);
        productMap.put("description", Desc);
        productMap.put("location", Loca);
        productMap.put("Rent", Rent);
        productMap.put("approvalRequest", "Registered");
        productMap.put("period", Period);
        productMap.put("Mfg date", Mfg);
        productMap.put("category", Ctgry);
        db.collection("product").document(productRandomKey).set(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        startActivity(new Intent(getApplicationContext(),homepage.class));

       /* db.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(pro_reg.this, homepage.class);
                            startActivity(intent);


                            Toast.makeText(pro_reg.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message = task.getException().toString();
                            Toast.makeText(pro_reg.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }



}