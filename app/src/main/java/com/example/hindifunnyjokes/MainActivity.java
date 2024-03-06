package com.example.hindifunnyjokes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hindifunnyjokes.Fragments.FavouriteFragment;
import com.example.hindifunnyjokes.Fragments.HomeFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    boolean exitFlag = false;
    Toolbar toolbar;
    Dialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolBar);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                for (String adapterClass : statusMap.keySet()) {
                    AdapterStatus status = statusMap.get(adapterClass);
                    Log.d("MyApp", String.format(
                            "Adapter name: %s, Description: %s, Latency: %d",
                            adapterClass, status.getDescription(), status.getLatency()));
                }


            }
        });



        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new HomeFragment());


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.itemHome) {
                    loadFragment(new HomeFragment());
                    getSupportActionBar().setTitle("Best Funny Jokes");
                } else if (id == R.id.itemFavorite) {
                    loadFragment(new FavouriteFragment());
                    getSupportActionBar().setTitle("Favorite Jokes");
                } else if (id == R.id.itemShare) {
                    shareAppLink();
                } else if (id == R.id.itemRate) {
                    rateOurApp();
                } else if (id == R.id.itemMoreApps) {
                    moreOurApp();
                } else if (id == R.id.disclamair) {
                    disc();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof FavouriteFragment) {
                // If the current fragment is FavouriteFragment, load HomeFragment
                loadFragment(new HomeFragment());
                getSupportActionBar().setTitle("Best Funny Jokes");
            } else {
                showDialog();
                if (exitFlag) {
                    super.onBackPressed();
                }
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle("Exit")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFlag = true;
                       finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitFlag = false;
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    // Option --> share app
    private void shareAppLink() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String body = "https://play.google.com/store/apps/details?id=" + getPackageName();
        String sub = "Your Subject";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, sub);
        myIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(myIntent, "Share Using"));
    }

    // Option --> Rate our app
    private void rateOurApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void disc() {
        customDialog = new Dialog(MainActivity.this);
        customDialog.setContentView(R.layout.dialog_disclaimer);
        customDialog.setCancelable(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnOk = customDialog.findViewById(R.id.tvBtnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    // Option --> More app
    private void moreOurApp() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:" + "Codingkey")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/developer?id=Codingkey")));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.disclaimer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // method for click on menu item

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.disclamair) {
            disc();
        }
        return super.onOptionsItemSelected(item);
    }
}