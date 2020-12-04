package com.example.donations.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;

import com.example.donations.Activities.LoginActivity;
import com.example.donations.Fragments.AcceptedDonationStudent;
import com.example.donations.Fragments.ApplyDonationStudent;
import com.example.donations.Fragments.DonorDashboard;
import com.example.donations.Fragments.PendingDonationStudent;
import com.example.donations.Fragments.RejectedDonationStudent;
import com.example.donations.Fragments.StudentDashboard;
import com.example.donations.MainActivity;
import com.example.donations.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BottomAppBar bottomAppBar;
    FloatingActionButton studentDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bottomAppBar = findViewById(R.id.bottom_app_bar);
        studentDashboard = findViewById(R.id.student_dashboard);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        FragmentTransaction firstload = getSupportFragmentManager().beginTransaction();
        firstload.replace(R.id.frame_student, new StudentDashboard(), "AddBillTag").commit();
        studentDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new StudentDashboard());
            }
        });
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.apply_student) {
            loadFragment(new ApplyDonationStudent());
        } else if (id == R.id.pending_student) {
            loadFragment(new PendingDonationStudent());
        }
        else if(id == R.id.accepted_student){
            loadFragment(new AcceptedDonationStudent());
        }
        else if(id == R.id.rejected_student){
            loadFragment(new RejectedDonationStudent());
        }
        else{
            startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean loadFragment(Fragment fragment) { //Switch fragments
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up) // Fade in/out animation
                    .replace(R.id.frame_student, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
