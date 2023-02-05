package com.example.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donationpage extends AppCompatActivity {

    private TextView name,NGOcode,email,phone,Addressngo;
    private ImageButton funds_button,clothes_button,food_button;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    String ngoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationpage);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        TextView name = findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("name"));

        TextView NGOcode = findViewById(R.id.NGOcode);
        NGOcode.setText(getIntent().getStringExtra("NGOcode"));

        TextView email = findViewById(R.id.email);
        email.setText(getIntent().getStringExtra("email"));

        TextView phone = findViewById(R.id.phone);
        phone.setText(getIntent().getStringExtra("phone"));

        TextView Addressngo = findViewById(R.id.Addressngo);
        Addressngo.setText(getIntent().getStringExtra("Addressngo"));

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        NGOcode.setText(intent.getStringExtra("NGOcode"));
        email.setText(intent.getStringExtra("email"));
        phone.setText(intent.getStringExtra("phone"));
        Addressngo.setText(intent.getStringExtra("Addressngo"));
        ngoId = intent.getStringExtra("ngoId");

        food_button = findViewById(R.id.food_button);
        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foodDonationIntent = new Intent(donationpage.this, fooddonationform.class);
                foodDonationIntent.putExtra("ngoId",ngoId);
                startActivity(foodDonationIntent);
            }
        });

        clothes_button = findViewById(R.id.clothes_button);
        clothes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clothDonationIntent = new Intent(donationpage.this, clothdonationform.class);
                clothDonationIntent.putExtra("ngoId",ngoId);
                startActivity(clothDonationIntent);
                finish();
            }
        });

        funds_button = findViewById(R.id.funds_button);
        funds_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fundDonationIntent = new Intent(donationpage.this,funddonationform.class);
                startActivity(fundDonationIntent);
                fundDonationIntent.putExtra("ngoId",ngoId);
                finish();
            }
        });
    }
}