package com.example.donate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class thankyoupage extends AppCompatActivity {
    private Button backbuttonh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyoupage);
        Button backbuttonh = findViewById(R.id.backbuttonh);

        backbuttonh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thankyoupage.this,dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}