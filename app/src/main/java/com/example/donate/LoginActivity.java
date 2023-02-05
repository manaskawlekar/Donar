package com.example.donate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{

    private TextView createnewAccount;

    private EditText inputEmail,inputPassword;
    private TextView forgotPassword;
    private Button btnLogin;

    private ImageButton fbbutton,gmbutton;
    ProgressDialog loader;
    private FirebaseAuth mAuth;

    //For Facebook
//    CallbackManager callbackManager;

    FirebaseAuth.AuthStateListener authStateListener;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createnewAccount = findViewById(R.id.createNewAccount);
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, dashboard.class);
                    startActivity(intent);
//                    finish();
                }
            }
        };

        gmbutton=findViewById(R.id.gmbutton);
        fbbutton=findViewById(R.id.fbbutton);
        inputEmail = findViewById(R.id.ngoEmail);
        inputPassword = findViewById(R.id.ngoPassword);
        forgotPassword = findViewById(R.id.forgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loader = new ProgressDialog(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //For facebook login

//        CallbackManager callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        startActivity(new Intent(LoginActivity.this,dashboard.class));
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });

        //For Facebook login
//        fbbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
//            }
//        });


        //Already Have an account
        createnewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //Login Button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String email = inputEmail.getText().toString().trim();
                 String password = inputPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    inputEmail.setError("Email is required!!");
                }
                if (password.isEmpty()) {
                    inputPassword.setError("Password is required!!");
                } else {
                    loader.setMessage("Log is in progress!!");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, dashboard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this,"Please Enter valid credentials", Toast.LENGTH_LONG).show();
                            }
                            loader.dismiss();
                        }
                    });
                }
            }
        });


        //Gmail sign in
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });

        gmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });
    }
    private void Signin()
    {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                task.getResult(ApiException.class);
                dashboard();
            }
            catch (ApiException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dashboard(){
        finish();
        Intent intent = new Intent(getApplicationContext(),dashboard.class);
        startActivity(intent);

    }

    @Override
        protected void onStart () {
            super.onStart();
            mAuth.addAuthStateListener(authStateListener);
        }

        @Override
        protected void onStop () {
            super.onStop();
            mAuth.removeAuthStateListener(authStateListener);
        }

        //For Facebook
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    }