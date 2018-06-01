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

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.geekhub.R;
import com.example.android.geekhub.adapters.ShopAdapter;
import com.example.android.geekhub.entities.Ad;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.entities.SpaceForAds;
import com.example.android.geekhub.enums.DesignType;
import com.example.android.geekhub.enums.Dimension;
import com.example.android.geekhub.enums.MaterialType;
import com.example.android.geekhub.enums.SpaceType;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.util.ArrayList;
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
/*    @BindView(R.id.recycler_view_bands)
    RecyclerView recyclerViewAds;*/

    ActionBar actionBar;
    List<Shop> shops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        ButterKnife.bind(this);

        setupActionBar();
        setupDrawer();
        setupContentFestivals();

        findViewById(R.id.img_help).setOnClickListener(view -> {
            switch (viewFlipper.getDisplayedChild()) {
                case 0:
                    Toast.makeText(MainActivity.this, "Here you can see a list of shops", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "Here you can add an ad", Toast.LENGTH_SHORT).show();
                    break;
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

        if (id == R.id.nav_shop_list) {
            viewFlipper.setDisplayedChild(0);
            setupContentFestivals();
        } else if (id == R.id.nav_add_ad) {
            new MaterialDialog.Builder(this)
                    .title("ADMIN FUNCTION")
                    .content("WAIT, THIS FUNCTION IS ONLY AVAILABLE IF YOU ARE THE ADMIN! ARE YOU?")
                    .positiveColorRes(R.color.colorPrimaryDark)
                    .negativeColorRes(R.color.colorPrimaryDark)
                    .positiveText("Ye, sure!")
                    .negativeText("No, i'm not")
                    .onNegative((dialog, which) -> item.setChecked(false))
                    .onPositive((dialog, which) -> {
                        viewFlipper.setDisplayedChild(1);
                        setupContentAddAD();
                    })
                    .show();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupContentAddAD() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Add ad");
    }

   /* private void setupContentAddBand() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Add band");
    }

    private void setupContentSearch() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Search");
    }

    private void setupContentBands() {
        setupRecyclerBands();
    }

    private void setupRecyclerBands() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Bands List");
        AdAdapter mAdapter = new AdAdapter(this, getAllAds());
        recyclerViewAds.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAds.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAds.getContext(),
                layoutManager.getOrientation());
        recyclerViewAds.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }*/

/*    private List<Ad> getAllAds() {
        for (Shop fest : shops) {
            for (Ad ad : fest.getAds()) {
                if (!allAds.contains(ad)) {
                    allAds.add(ad);
                }
            }
        }
        allAds = Stream.of(allAds)
                .sorted((b1, b2) -> b1.getName().compareTo(b2.getName()))
                .toList();
        return allAds;
    }*/

    private void setupContentFestivals() {
        // TODO: LOAD FESTS AND BANDS FROM DB
        shops.clear();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        Date startDate1 = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date endDate1 = cal.getTime();

        List<Ad> addsSilpo = Arrays.asList(
                new Ad("FOTIUS", startDate1, endDate1, Dimension.LARGE, MaterialType.PAPER, DesignType.EXPENSIVE),
                new Ad("SKIDKA NA SHAMPYN", startDate1, endDate1, Dimension.LARGE, MaterialType.METAL, DesignType.EXPENSIVE)
        );

        List<SpaceForAds> spaceForAdsSilpo = Arrays.asList(
                new SpaceForAds(SpaceType.WALL, Dimension.LARGE),
                new SpaceForAds(SpaceType.STAND, Dimension.LARGE),
                new SpaceForAds(SpaceType.WALL, Dimension.SMALL)
        );

        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        Date startDate2 = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 6);
        Date endDate2 = cal.getTime();

        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 4);
        Date startDate3 = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        Date endDate3 = cal.getTime();

        List<Ad> addsAtb = Arrays.asList(
                new Ad("FOTIUS", startDate1, endDate1, Dimension.SMALL, MaterialType.PAPER, DesignType.CHEAP),
                new Ad("SKIDKA NA OCHKI", startDate2, endDate2, Dimension.SMALL, MaterialType.METAL, DesignType.EXPENSIVE),
                new Ad("Summer festival ad",  startDate2, endDate2, Dimension.SMALL, MaterialType.PAPER, DesignType.CHEAP),
                new Ad("NEW iPHONE", startDate3, endDate3, Dimension.SMALL, MaterialType.METAL, DesignType.CHEAP)
        );
        List<SpaceForAds> spacesForAdsAtb = Arrays.asList(
                new SpaceForAds(SpaceType.WALL, Dimension.SMALL),
                new SpaceForAds(SpaceType.WALL, Dimension.SMALL),
                new SpaceForAds(SpaceType.WALL, Dimension.SMALL)
        );

        shops.addAll(Arrays.asList(
                new Shop("Silpo", addsSilpo, spaceForAdsSilpo),
                new Shop("ATB", addsAtb, spacesForAdsAtb)
        ));
        setupRecyclerShops();
    }

    private void setupRecyclerShops() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Shops List");
        ShopAdapter mAdapter = new ShopAdapter(this, shops, this);
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
        if (viewFlipper.getDisplayedChild() == 0) {
            Shop chosenShop = shops.get(position);
            Intent intent = new Intent(getApplicationContext(), DetailedShopInfoActivity.class);
            intent.putExtra("shop", chosenShop);
            startActivity(intent);
        } else if (viewFlipper.getDisplayedChild() == 1) {
            // TODO Add new ad;
        }
    }
}
