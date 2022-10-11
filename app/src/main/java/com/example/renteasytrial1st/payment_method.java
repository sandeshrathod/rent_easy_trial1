package com.example.renteasytrial1st;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class payment_method extends AppCompatActivity {
    Button btn_p;
RadioButton rad,rad2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        rad =  findViewById(R.id.radioButton);
        rad2 =  findViewById(R.id.radioButton2);


        Button showbtn = findViewById(R.id.makePayment);
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rad.isChecked()){
                    startActivity(new Intent(getApplicationContext(),payment_mod.class));
                }
                if(rad2.isChecked())
            {
                startActivity(new Intent(getApplicationContext(),homepage.class));

                }
                else{}

            }
        });
    }
 /*   public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String msg = "";
        // We'll check which radiobutton is clicked
        switch (view.getId()) {
            case R.id.rbBhop:
                if (checked)
                    msg = "You Clicked Bhopal";
                break;
            case R.id.rbbang:
                if (checked)
                    msg = "You Clicked Bangalore ";
                break;
            case R.id.rbDelhi:
                if (checked)
                    msg = "You Clicked Delhi ";
                break;
            case R.id.rbmum:
                if (checked)
                    msg = "You Clicked Mumbai";
                break;
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }*/
}