package com.example.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseUser;

public class fooddonationform extends AppCompatActivity {
    private EditText donorname,donoraddress,donorphone,food_quantity,et_additional_notes;
    private Spinner sp_food_type;
    private TextView foodform;
    private Button submit;

    private ImageButton home_button;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference myRef = database.getReference();
    String ngoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddonationform);

        Intent intent = getIntent();
        ngoId = intent.getStringExtra("ngoId");

        donorname=findViewById(R.id.donorname);
        donoraddress=findViewById(R.id.donoraddress);
        donorphone=findViewById(R.id.donorphone);
        food_quantity=findViewById(R.id.food_quantity);
        et_additional_notes=findViewById(R.id.et_additional_notes);
        sp_food_type=findViewById(R.id.sp_food_type);
        foodform=findViewById(R.id.foodform);
        submit=findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String phone = currentUser.getPhoneNumber();

            donorname.setText(name);
            donorphone.setText(phone);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String name = donorname.getText().toString();
                    String address = donoraddress.getText().toString();
                    String phone = donorphone.getText().toString();
                    String quantity = food_quantity.getText().toString();
                    String descriptions = et_additional_notes.getText().toString();
                    String foodType = sp_food_type.getSelectedItem().toString();

                    ClothDonation cd = new ClothDonation(name, address, phone, quantity, descriptions, foodType);

                    String key = myRef.child("fooddonation").child(ngoId).push().getKey();
                    myRef.child("fooddonation").child(ngoId).child(key).setValue(cd);
                    Toast.makeText(fooddonationform.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();

                    finish();

                    startActivity(new Intent(fooddonationform.this,thankyoupage.class));
                }
                catch(Exception e)
                {
                    Log.d("mResult",e.getMessage());
                }


            }
        });
        ImageButton homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToHome();
            }
        });
    }

    public void navigateToHome() {
        // Add code here to navigate to the home screen
        finish();
    }
}