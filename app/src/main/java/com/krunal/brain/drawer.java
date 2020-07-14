package com.krunal.brain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.krunal.brain.Fragments.AddIdeaFragment;
import com.krunal.brain.Fragments.EditProfileFragment;
import com.krunal.brain.Fragments.HomeIdeaFragment;
import com.krunal.brain.Fragments.IdeasUserFragment;
import com.krunal.brain.Fragments.TodoIdeasFragment;

public class drawer extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private Button buttonHome;
    private Button buttonAddNewIdea;
    private Button buttonUserIdea;
    private Button buttonUserToDo;
    private Button buttonUpdateProfile;
    private Button buttonSignOut;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        init();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void init(){
        buttonHome = findViewById(R.id.buttonHome);
        buttonAddNewIdea = findViewById(R.id.buttonAddNewIdea);
        buttonUserIdea = findViewById(R.id.buttonUserIdea);
        buttonUserToDo = findViewById(R.id.buttonUserToDo);
        buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonSignOut = findViewById(R.id.buttonSignOut);

        buttonHome.setOnClickListener(this);
        buttonAddNewIdea.setOnClickListener(this);
        buttonUserIdea.setOnClickListener(this);
        buttonUserToDo.setOnClickListener(this);
        buttonUpdateProfile.setOnClickListener(this);
        buttonSignOut.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                HomeIdeaFragment.newInstance(), HomeIdeaFragment.class.getSimpleName()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        HomeIdeaFragment.newInstance(), HomeIdeaFragment.class.getSimpleName()).commit();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.buttonAddNewIdea:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        AddIdeaFragment.newInstance(), AddIdeaFragment.class.getSimpleName()).commit();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.buttonUserIdea:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        IdeasUserFragment.newInstance(), IdeasUserFragment.class.getSimpleName()).commit();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.buttonUserToDo:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        TodoIdeasFragment.newInstance(), TodoIdeasFragment.class.getSimpleName()).commit();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.buttonUpdateProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        EditProfileFragment.newInstance(), EditProfileFragment.class.getSimpleName()).commit();
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.buttonSignOut:
                new PreferenceManager(com.krunal.brain.drawer.this).clear();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}