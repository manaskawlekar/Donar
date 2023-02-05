package com.example.donate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
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
import android.util.Log;
import android.view.MenuInflater;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class dashboardngo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dashngo;
    private NavigationView nav_view;
    private Toolbar toolbar2;
    private TextView nav_user_name, nav_user_email, nav_user_type, nav_user_NGOcode;
    DatabaseReference userRef;
    private SearchView searchview;
    private RecyclerView recyclerview2;
    private myadapter2 adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardngo);

        recyclerview2 = findViewById(R.id.recyclerview2);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("Donation App");

        dashngo = findViewById(R.id.dashngo);
        nav_view = findViewById(R.id.nav_view);
        searchview = findViewById(R.id.searchview2);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(dashboardngo.this, dashngo,toolbar2,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dashngo.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        nav_user_name = nav_view.getHeaderView(0).findViewById(R.id.nav_user_name);
        nav_user_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_user_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);
        nav_user_NGOcode= nav_view.getHeaderView(0).findViewById(R.id.nav_user_NGOcode);

        userRef = FirebaseDatabase.getInstance().getReference().child("Org").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    nav_user_name.setText(name);

                    String email = snapshot.child("email").getValue().toString();
                    nav_user_email.setText(email);

                    String type = snapshot.child("type").getValue().toString();
                    nav_user_type.setText(type);

                    String NGOcode = snapshot.child("NGOcode").getValue().toString();
                    nav_user_NGOcode.setText(NGOcode);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //For DONOR list to get Displayed
        recyclerview2.setLayoutManager(new LinearLayoutManager(this
        ));
        FirebaseRecyclerOptions<model2> options =
                new FirebaseRecyclerOptions.Builder<model2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), model2.class)
                        .build();
        adapter2=new myadapter2(options);
        recyclerview2.setAdapter(adapter2);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Intent home = new Intent(dashboardngo.this,dashboardngo.class);
                startActivity(home);
                finish();
                break;

            case R.id.profile:
                Intent prof = new Intent(dashboardngo.this, profileactiivityngo.class);
                startActivity(prof);
                finish();
                break;

            case R.id.about:
                Intent abt = new Intent(dashboardngo.this,aboutdonate.class);
                startActivity(abt);
                break;

            case R.id.youraddress:
                Intent addr = new Intent(dashboardngo.this,ngouraddress.class);
                startActivity(addr);
                break;

            case R.id.donorslist:
                Intent donlist = new Intent(dashboardngo.this,donorlistngo.class);
                startActivity(donlist);
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
                Intent i = new Intent(dashboardngo.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        dashngo.closeDrawer(GravityCompat.START);
        return true;
    }
}