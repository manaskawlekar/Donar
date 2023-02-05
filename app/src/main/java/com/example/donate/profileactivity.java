package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileactivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView email, fullname, phone,type;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        type = findViewById(R.id.type);
        backButton = findViewById(R.id.backbutton);


        //Create a reference to the user's profile information in the Firebase database
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            //Add a ValueEventListener to the reference to retrieve the user's profile information when the data changes
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        //Retrieve and set the values for each TextView
                         email.setText(snapshot.child("email").getValue().toString());
                         fullname.setText(snapshot.child("fullname").getValue().toString());
                         phone.setText(snapshot.child("phone").getValue().toString());
                         type.setText(snapshot.child("type").getValue().toString());
                    }
                    if (snapshot.hasChild("type")) {
                        type.setText(snapshot.child("type").getValue().toString());
                    } else {
                        // Handle case where the 'type' field is not present in the snapshot
                        type.setText("N/A");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//Create an intent to navigate to the dashboard
                    Intent intent = new Intent(profileactivity.this, dashboard.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}