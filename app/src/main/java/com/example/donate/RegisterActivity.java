package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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

    Boolean passwordVisible;

    private Button btnRegister;

    ProgressDialog loader;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);
        loader = new ProgressDialog(this);


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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {

                mAuth = FirebaseAuth.getInstance();

                String email = emailid.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmpassword = inputConfirmPassword.getText().toString().trim();
                String fullname = fullName.getText().toString().trim();
                String contact = phone.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

                if(!password.equals(confirmpassword)){
                    inputConfirmPassword.setError("Passwords do not match");
                    return;
                }

                if (!email.matches(emailPattern)) {
                    emailid.setError("Invalid email address");
                    return;
                }
                if (contact.length() != 10) {
                    phone.setError("Enter a valid 10-digit phone number");
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
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("fullname", fullname);
                                userMap.put("phone", contact);
                                userMap.put("email", email);
                                userMap.put("password", password);
                                userMap.put("type","Donor");

                                userDatabaseRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            loader.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            finish();
                                        }
                                        else{
                                            loader.dismiss();
                                            String message = task.getException().toString();
                                            Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                loader.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}