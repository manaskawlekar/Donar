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

public class clothdonationform extends AppCompatActivity {
    private EditText clothdonorname,clothdonoraddress,clothdonorphone,cloth_quantity,cloth_descriptions;
    private Spinner sp_cloth_type;
    private TextView clothform;
    private Button csubmit;

    private ImageButton home_button;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference myRef = database.getReference();
    String ngoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothdonationform);

        Intent intent = getIntent();
        ngoId = intent.getStringExtra("ngoId");

        clothdonorname=findViewById(R.id.clothdonorname);
        clothdonoraddress=findViewById(R.id.clothdonoraddress);
        clothdonorphone=findViewById(R.id.clothdonorphone);
        cloth_quantity=findViewById(R.id.cloth_quantity);
        cloth_descriptions=findViewById(R.id.cloth_descriptions);
        sp_cloth_type=findViewById(R.id.sp_cloth_type);
        clothform=findViewById(R.id.clothform);
        csubmit=findViewById(R.id.csubmit);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String phone = currentUser.getPhoneNumber();

            clothdonorname.setText(name);
            clothdonorphone.setText(phone);
        }
        csubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                String donorname = clothdonorname.getText().toString();
                String address = clothdonoraddress.getText().toString();
                String phone = clothdonorphone.getText().toString();
                String quantity = cloth_quantity.getText().toString();
                String descriptions = cloth_descriptions.getText().toString();
                String clothType = sp_cloth_type.getSelectedItem().toString();

                ClothDonation cd = new ClothDonation(donorname, address, phone, quantity, descriptions,clothType);

                    String key = myRef.child("clothdonation").child(ngoId).push().getKey();
                    myRef.child("clothdonation").child(ngoId).child(key).setValue(cd);
                    Toast.makeText(clothdonationform.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(clothdonationform.this,thankyoupage.class));
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