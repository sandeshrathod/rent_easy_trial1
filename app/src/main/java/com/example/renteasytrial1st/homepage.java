package com.example.renteasytrial1st;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {
    public DrawerLayout drawerLayout;
   public NavigationView nav;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    ArrayList<product> userArrayList;
    MyAdapter myAdapter;
    Spinner fcatgry_spn,floca_spn ;
/*    ProgressDialog progressDialog;*/
Button btnn;
  FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        fcatgry_spn =findViewById(R.id.filtr_ctr_spn);
        btnn = findViewById(R.id.btn_reg);
        floca_spn = findViewById(R.id.loca_spn);
        /////////////
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("electronics");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fcatgry_spn.setAdapter(adapter);
        ///////////////////////////////////
        List<String> location = new ArrayList<String>();
        location.add("pune");
        location.add("Jalgaon");
        location.add("Mumbai");
        location.add("Delhi");
        location.add("Bengluru");
        location.add("Hydrabad");
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floca_spn.setAdapter(adap);
        //////////////////////////////////
        nav = (NavigationView) findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_account:
                        startActivity(new Intent(homepage.this,edit_profile.class));
                        Toast.makeText(getApplicationContext(), "Home Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        startActivity(new Intent(homepage.this,feedback.class));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_pro_reg:
                        startActivity(new Intent(homepage.this,pro_reg.class));
                        Toast.makeText(getApplicationContext(), "Setting Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_myordr:
                        startActivity(new Intent(homepage.this,myordr.class));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });


        recyclerView = findViewById(R.id.recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();

      userArrayList = new ArrayList<product>();
      myAdapter= new MyAdapter(homepage.this,userArrayList);
        recyclerView.setAdapter(myAdapter);

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventChangeListener();
            }
        });
}

    private void EventChangeListener() {
     String Loca = (String)floca_spn.getSelectedItem();

        db.collection("product")
              //  .whereEqualTo("location", Loca)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null){


                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                userArrayList.add(dc.getDocument().toObject(product.class));

                            }

                            myAdapter.notifyDataSetChanged();

                  }

                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}