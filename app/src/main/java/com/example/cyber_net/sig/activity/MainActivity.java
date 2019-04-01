package com.example.cyber_net.sig.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cyber_net.sig.R;
import com.example.cyber_net.sig.fragment.Galeri;
import com.example.cyber_net.sig.fragment.ManfaatPegunungan;
import com.example.cyber_net.sig.fragment.PosPendakian;
import com.example.cyber_net.sig.fragment.SafetyAndRescue;
import com.example.cyber_net.sig.fragment.WisataGunung;
import com.example.cyber_net.sig.shared.SharedLogin;

import static com.example.cyber_net.sig.shared.SharedLogin.SP_SUDAH_LOGIN2;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager = getSupportFragmentManager();
    SharedLogin sharedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cara memngubah icon
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_settings_black_24dp));
        sharedLogin  = new SharedLogin(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager.beginTransaction().replace(R.id.frame, new WisataGunung()).commit();
        getSupportActionBar().setTitle(getResources().getString(R.string.wisata_gunung));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragmentManager.beginTransaction().replace(R.id.frame, new WisataGunung()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.wisata_gunung));
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.frame, new ManfaatPegunungan()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.manfaat));
        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.frame, new SafetyAndRescue()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.safety));
        } else if (id == R.id.nav_image) {
            fragmentManager.beginTransaction().replace(R.id.frame, new Galeri()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.galeri));
        }else if (id == R.id.nav_pos) {
            fragmentManager.beginTransaction().replace(R.id.frame, new PosPendakian()).commit();
            getSupportActionBar().setTitle(getResources().getString(R.string.pos));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_keluar) {
            sharedLogin.saveSPBoolean(SP_SUDAH_LOGIN2, false);
            startActivity(new Intent(MainActivity.this, Login.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
