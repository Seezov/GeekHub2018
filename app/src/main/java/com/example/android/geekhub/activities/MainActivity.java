package com.example.android.geekhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.android.geekhub.R;
import com.example.android.geekhub.adapters.FestivalAdapter;
import com.example.android.geekhub.entities.Band;
import com.example.android.geekhub.entities.Festival;
import com.example.android.geekhub.enums.Genre;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.recycler_view_festivals)
    RecyclerView recyclerViewFestivals;

    ActionBar actionBar;
    List<Festival> festivals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        ButterKnife.bind(this);

        setupActionBar();
        setupDrawer();
        setupContentFestivals();

        findViewById(R.id.img_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "HELP ME!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setupActionBar() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_toolbar));
        actionBar.setCustomView(R.layout.actionbar_default);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_fest_list) {
            viewFlipper.setDisplayedChild(0);
            setupContentFestivals();
        } else if (id == R.id.nav_band_list) {
            viewFlipper.setDisplayedChild(1);
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_add_fest) {

        } else if (id == R.id.nav_add_band) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupContentFestivals() {
        // TODO: LOAD FESTS AND BANDS FROM DB
        List<Band> bandsRAM = Arrays.asList(
                new Band("Foo Fighters", Arrays.asList(Genre.ROCK)),
                new Band("Red Hot Chili Peppers", Arrays.asList(Genre.FUNK, Genre.ROCK)),
                new Band("Pendulum", Arrays.asList(Genre.ELECTRONIC, Genre.ROCK)));

        List<Band> bandsAftershock = Arrays.asList(
                new Band("System Of A Down", Arrays.asList(Genre.METAL)),
                new Band("Seether", Arrays.asList(Genre.POST_GRUNGE, Genre.ROCK)),
                new Band("Eminem", Arrays.asList(Genre.RAP, Genre.POP)));

        List<Band> bandsFaine = Arrays.asList(
                new Band("The Rasmus", Arrays.asList(Genre.ROCK, Genre.POP)),
                new Band("The Gitas", Arrays.asList(Genre.POST_GRUNGE, Genre.ROCK)),
                new Band("DASH", Arrays.asList(Genre.METAL, Genre.RAP)));

        List<Band> bandsResp = Arrays.asList(
                new Band("IGNEA", Arrays.asList(Genre.METAL)),
                new Band("Vivienne Mort", Arrays.asList(Genre.POP, Genre.ROCK)),
                new Band("AHHA", Arrays.asList(Genre.METAL, Genre.RAP)));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDateRAM = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date endDateRAM = cal.getTime();

        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        Date startDateAftershock = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        Date endDateAftershock = cal.getTime();

        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        Date startDateFaine = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 22);
        Date endDateFaine = cal.getTime();

        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startResp = cal.getTime();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date endDateResp = cal.getTime();

        festivals = Arrays.asList(
                new Festival(
                        "Rock am Ring",
                        "Nürburg, Germany",
                        230f,
                        bandsRAM, startDateRAM, endDateRAM),
                new Festival(
                        "Aftershock",
                        "California, United States",
                        150f,
                        bandsAftershock, startDateAftershock, endDateAftershock),
                new Festival(
                        "RespublicaFEST",
                        "Kamyanets-Podilsky, Ukraine",
                        17f,
                        bandsResp, startResp, endDateResp),
                new Festival(
                        "Faine Misto",
                        "Ternopil, Ukraine",
                        35f,
                        bandsFaine, startDateFaine, endDateFaine)
        );
        setupRecycler();
    }

    private void setupRecycler() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Festivals List");
        FestivalAdapter mAdapter = new FestivalAdapter(this, festivals, this);
        recyclerViewFestivals.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFestivals.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFestivals.getContext(),
                layoutManager.getOrientation());
        recyclerViewFestivals.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Festival chosenFestival = festivals.get(position);
        Intent intent = new Intent(getApplicationContext(), DetailedFestivalInfoActivity.class);
        intent.putExtra("festival", chosenFestival);
        startActivity(intent);
    }
}
