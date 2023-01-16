package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NGOlogin extends AppCompatActivity {

    TextView createnewAccount;
    EditText ngoEmail,ngoPassword;
    TextView forgotPassword;
    Button btnLogin;
    ProgressDialog loader;
    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngologin);
        createnewAccount=findViewById(R.id.createNewAccount);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              FirebaseUser user = mAuth.getCurrentUser();
              if (user !=null){
                  Intent intent = new Intent(NGOlogin.this,dashboard.class);
                  startActivity(intent);
                  finish();
              }
            }
        };
        ngoEmail=findViewById(R.id.ngoEmail);
        ngoPassword=findViewById(R.id.ngoPassword);
        forgotPassword=findViewById(R.id.forgotPassword);
        btnLogin=findViewById(R.id.btnLogin);
        loader = new ProgressDialog(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOlogin.this,NGOregister.class));
            }
        });

        ngoEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ngoEmail.getText().toString().trim();
                final String password = ngoPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                   ngoEmail.setError("Email is required!!!");
                }
                if (TextUtils.isEmpty(password)){
                    ngoPassword.setError("Password is required!!");
                }

                else{
                    loader.setMessage("Log is in progress!!");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(NGOlogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(NGOlogin.this,dashboard.class);
                             startActivity(intent);
                             finish();
                         }else{
                             Toast.makeText(NGOlogin.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                         }
                         loader.dismiss();
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}