package com.example.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ngouraddress extends AppCompatActivity {

    private EditText editTextStreetAddress,editTextCity,editTextState,editTextPostalCode,editTextCountry,editTextLandmark;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngouraddress);

        editTextStreetAddress = findViewById(R.id.editTextStreetAddress);
        editTextCity = findViewById(R.id.editTextCity);
        editTextState = findViewById(R.id.editTextState);
        editTextPostalCode = findViewById(R.id.editTextPostalCode);
        editTextCountry = findViewById(R.id.editTextCountry);
        editTextLandmark = findViewById(R.id.editTextLandmark);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String streetAddress = editTextStreetAddress.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();
                String state = editTextState.getText().toString().trim();
                String postalCode = editTextPostalCode.getText().toString().trim();
                String country = editTextCountry.getText().toString().trim();
                String landmark = editTextLandmark.getText().toString().trim();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference addressRef = FirebaseDatabase.getInstance().getReference("Org").child(userId).child("Address");
                    Addressngo address = new Addressngo(streetAddress, city, state, postalCode, country, landmark);
                    addressRef.setValue(address);
                    Toast.makeText(ngouraddress.this, "Address added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}