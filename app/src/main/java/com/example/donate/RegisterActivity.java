package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextView alreadyHaveaccount;

    private EditText fullName,phone,emailid,inputPassword,inputConfirmPassword;

    private Button btnRegister;

    ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);


        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        fullName = findViewById(R.id.fullName);
        phone = findViewById(R.id.phone);
        emailid = findViewById(R.id.emailid);
        inputPassword = findViewById(R.id.ngoPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {

             String email = emailid.getText().toString().trim();
             String password = inputPassword.getText().toString().trim();
             String confirmpassword = inputConfirmPassword.getText().toString().trim();
             String fullname = fullName.getText().toString().trim();
             String contact = phone.getText().toString().trim();

            if(email.isEmpty()){
                emailid.setError("Email is required!!");
                return;
            }

            if(password.isEmpty()){
                inputPassword.setError("Password is required!!");
                return;
            }

            if(confirmpassword.isEmpty()){
                inputConfirmPassword.setError("Re-Enter password is required!!");
                return;
            }

            if(fullname.isEmpty()){
                fullName.setError("Full name is required!!");
                return;
            }

            if(contact.isEmpty()){
                phone.setError("Mobile no./ Contact no. is required!!");
                return;
            }

            else{
                loader.setMessage("Registering You!!");
                loader.setCanceledOnTouchOutside(false);
                loader.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String error = task.getException().toString();
                            Toast.makeText(RegisterActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String CurrentUserId = mAuth.getCurrentUser().getUid();
                            userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUserId);
                            HashMap userInfo = new HashMap();
                            userInfo.put("id",CurrentUserId);
                            userInfo.put("fullname",fullname);
                            userInfo.put("email",email);
                            userInfo.put("password",password);
                            userInfo.put("confirmpassword",confirmpassword);
                            userInfo.put("contact",phone);
                            userInfo.put("type","Customer");

                            userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Data set Successfully", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                    //loader.dismiss();
                                }
                            });
                            Intent intent = new Intent(RegisterActivity.this, dashboard.class);
                            startActivity(intent);
                            finish();
                            loader.dismiss();
                        }

                    }
                });
            }
            }
        });
    }
}