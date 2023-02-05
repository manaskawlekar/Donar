package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
//import androidx.appcompat.app.AlertDialogLayout;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dash;
    private Toolbar toolbar;
    private NavigationView nav_view;
    RecyclerView recyclerview;
    private TextView nav_user_fullname, nav_user_email, nav_user_type;
    DatabaseReference userRef;
    myadapter adapter;
    private SearchView searchview;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        RecyclerView recyclerview=findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donation App");

        dash = findViewById(R.id.dash);
        nav_view = findViewById(R.id.nav_view);
        searchview = findViewById(R.id.searchview);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(dashboard.this, dash,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dash.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        nav_user_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_user_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_user_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do the search here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the adapter data here based on the newText
                return false;
            }
        });


        //In this when we login with normal email and password data of email,full name,type get fetched.
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String fullname = snapshot.child("fullname").getValue().toString();
                    nav_user_fullname.setText(fullname);

                    String email = snapshot.child("email").getValue().toString();
                    nav_user_email.setText(email);

                    String type = snapshot.child("type").getValue().toString();
                    nav_user_type.setText(type);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //For NGO list to get Displayed
        recyclerview.setLayoutManager(new LinearLayoutManager(this
        ));
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Org"), model.class)
                        .build();
        adapter=new myadapter(options);
        recyclerview.setAdapter(adapter);



        //Google Sign
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account !=null)
        {
            String fullname = account.getDisplayName();
            nav_user_fullname.setText(fullname);

            String email = account.getEmail();
            nav_user_email.setText(email);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //For DrawerLayout menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Intent Ho = new Intent(dashboard.this,dashboard.class);
                startActivity(Ho);
                break;

            case R.id.profile:
                Intent intent = new Intent(dashboard.this, profileactivity.class);
                startActivity(intent);
                break;

            case R.id.sentEmail:
                Intent senem = new Intent(this,dashboard.class);
                startActivity(senem);
                break;

            case R.id.transhistory:
                Intent trahis = new Intent(this, dashboard.class);
                startActivity(trahis);
                break;

            case R.id.about:
                Intent abt = new Intent(dashboard.this,aboutdonate.class);
                startActivity(abt);
                break;

            case R.id.share:
                // handle share item click
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app, it's awesome! https://play.google.com/store/apps/details?id=com.example.donate");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(dashboard.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        dash.closeDrawer(GravityCompat.START);
        return true;
    }
}