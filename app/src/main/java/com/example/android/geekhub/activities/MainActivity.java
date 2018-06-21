package com.example.android.geekhub.activities;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.geekhub.R;
import com.example.android.geekhub.adapters.ShopAdapter;
import com.example.android.geekhub.dao.AdDAO;
import com.example.android.geekhub.dao.AdsInShopsDAO;
import com.example.android.geekhub.dao.DesignDAO;
import com.example.android.geekhub.dao.DimensionDAO;
import com.example.android.geekhub.dao.MaterialDAO;
import com.example.android.geekhub.dao.OrderDAO;
import com.example.android.geekhub.dao.ShopDAO;
import com.example.android.geekhub.dao.SpaceDAO;
import com.example.android.geekhub.dao.SpacesForAdsDAO;
import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Ad;
import com.example.android.geekhub.entities.Design;
import com.example.android.geekhub.entities.Dimension;
import com.example.android.geekhub.entities.Material;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.enums.MaterialType;
import com.example.android.geekhub.enums.SpaceType;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @BindView(R.id.recycler_view_shops)
    RecyclerView recyclerViewShops;

    @BindView(R.id.btn_add_ad)
    Button btnAddAd;
    @BindView(R.id.f_ad_name)
    EditText fAdName;
    @BindView(R.id.f_start_date)
    TextView txtStartDate;
    @BindView(R.id.btn_add_start_date)
    Button btnAddStartDate;
    @BindView(R.id.f_end_date)
    TextView txtEndDate;
    @BindView(R.id.btn_add_end_date)
    Button btnAddEndDate;
    @BindView(R.id.dimensions_spinner)
    Spinner dimensionsSpinner;
    @BindView(R.id.designs_type_spinner)
    Spinner designsSpinners;
    @BindView(R.id.materials_spinner)
    Spinner materialSpinner;
    @BindView(R.id.shops_spinner)
    Spinner shopsSpinner;
    @BindView(R.id.ads_spinner)
    Spinner adsSpinner;

    private ShopDAO mShopDao;
    private AdDAO mAdDao;
    private DimensionDAO mDimensionDAO;
    private SpaceDAO mSpaceDAO;
    private SpacesForAdsDAO mSpacesForAdsDAO;
    private AdsInShopsDAO mAdsInShopsDAO;
    private OrderDAO mOrderDAO;
    private MaterialDAO mMaterialDAO;
    private DesignDAO mDesignDAO;

    DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

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
        // fill the listView
        mShopDao = new ShopDAO(this);
        mAdDao = new AdDAO(this);
        mDimensionDAO = new DimensionDAO(this);
        mSpaceDAO = new SpaceDAO(this);
        mSpacesForAdsDAO = new SpacesForAdsDAO(this);
        mAdsInShopsDAO = new AdsInShopsDAO(this);
        mOrderDAO = new OrderDAO(this);
        mMaterialDAO = new MaterialDAO(this);
        mDesignDAO = new DesignDAO(this);
        shops.clear();
        shops = mShopDao.getAllShops();
        if (shops != null && !shops.isEmpty()) {
            setupRecyclerShops();
        } else {
            setupContentShops();
        }

        //setupContentShops();

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

        setupDatePicker();

        setupSpinners();

        btnAddAd.setOnClickListener(view -> {
            if (adFieldsAreCorrect()) {
                addAdd();
            }
        });
    }

    private boolean adFieldsAreCorrect() {
        String adName = fAdName.getText().toString();
        if (adName.equals("")) {
            Toast.makeText(this, "Please enter an ad name!", Toast.LENGTH_SHORT).show();
        } else if (startDateIfInPast()){
            Toast.makeText(this, "Please enter a start date that isn't in the past!", Toast.LENGTH_SHORT).show();
        } else if (endDateIfBeforeStartDate()){
            Toast.makeText(this, "Please enter an end date that isn't before start date!", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    private boolean endDateIfBeforeStartDate() {
        String startDateString = txtStartDate.getText().toString();
        String endDateString = txtEndDate.getText().toString();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormatter.parse(startDateString);
            endDate = dateFormatter.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate.before(startDate);
    }

    private boolean startDateIfInPast() {

        String startDate = txtStartDate.getText().toString();
        Date enteredDate = null;
        try {
            enteredDate = dateFormatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date nowDate = Calendar.getInstance().getTime();
        Date todayDate = new Date(nowDate.getYear(), nowDate.getMonth(), nowDate.getDate());

        return enteredDate.before(todayDate);
    }

    private void addAdd() {
        Ad newAd = null;
        try {
           newAd  = new Ad(fAdName.getText().toString(),
                    dateFormatter.parse(txtStartDate.getText().toString()),
                    dateFormatter.parse(txtEndDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAdDao.createAd(newAd.getName(), newAd.getStartDate().getTime(), newAd.getEndDate().getTime());
        Toast.makeText(this, "Ad is added!", Toast.LENGTH_SHORT).show();
        setupAdSpinner();
    }

    private void setupSpinners() {
        setupDimensionSpinner();

        setupDesignSpinner();

        setupMaterialSpinner();

        setupShopSpinner();

        setupAdSpinner();
    }

    //region Spinners
    private void setupAdSpinner() {
        // Ad spinner
        List<Ad> ads = mAdDao.getAllAds();
        List<String> adNames = new ArrayList<>();
        for (Ad d : ads) {
            adNames.add(d.getName());
        }
        ArrayAdapter<String> adapterAd = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, adNames);
        adapterAd.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adsSpinner.setAdapter(adapterAd);
    }

    private void setupShopSpinner() {
        // Shop spinner
        List<Shop> shops = mShopDao.getAllShops();
        List<String> shopNames = new ArrayList<>();
        for (Shop s : shops) {
            shopNames.add(s.getName());
        }
        ArrayAdapter<String> adapterShop = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, shopNames);
        adapterShop.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        shopsSpinner.setAdapter(adapterShop);
    }

    private void setupMaterialSpinner() {
        // Material spinner
        List<Material> materials = mMaterialDAO.getAllMaterials();
        List<String> materialNames = new ArrayList<>();
        for (Material m : materials) {
            materialNames.add(m.getName());
        }
        ArrayAdapter<String> adapterMaterial = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, materialNames);
        adapterMaterial.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        materialSpinner.setAdapter(adapterMaterial);
    }

    private void setupDesignSpinner() {
        // Design spinner
        List<Design> designs = mDesignDAO.getAllDesigns();
        List<String> designNames = new ArrayList<>();
        for (Design d : designs) {
            designNames.add(d.getName());
        }
        ArrayAdapter<String> adapterDesigns = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, designNames);
        adapterDesigns.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        designsSpinners.setAdapter(adapterDesigns);
    }

    private void setupDimensionSpinner() {
        // Dim spinner
        List<Dimension> dimensions = mDimensionDAO.getAllDimensions();
        List<String> dimensionNames = new ArrayList<>();
        for (Dimension d : dimensions) {
            dimensionNames.add(d.getName());
        }
        ArrayAdapter<String> adapterDimensions = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dimensionNames);
        adapterDimensions.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dimensionsSpinner.setAdapter(adapterDimensions);
    }

    private void setupDatePicker() {
        Calendar newCalendar = Calendar.getInstance();

        try {
            txtStartDate.setText(dateFormatter.format(dateFormatter.parse(dateFormatter.format(newCalendar.getTime()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            txtEndDate.setText(dateFormatter.format(dateFormatter.parse(dateFormatter.format(newCalendar.getTime()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog startTime = new DatePickerDialog(this, R.style.DialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            txtStartDate.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btnAddStartDate.setOnClickListener(v -> startTime.show());

        DatePickerDialog endTime = new DatePickerDialog(this, R.style.DialogTheme, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            txtEndDate.setText(dateFormatter.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        btnAddEndDate.setOnClickListener(v -> endTime.show());
    }
    //end region

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
            setupRecyclerShops();
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
                        // TODO FIX THIS
                        setupRecyclerAddAd();
                    })
                    .show();
        } else if (id == R.id.nav_add_design) {
            new MaterialDialog.Builder(this)
                    .title("ADMIN FUNCTION")
                    .content("WAIT, THIS FUNCTION IS ONLY AVAILABLE IF YOU ARE THE ADMIN! ARE YOU?")
                    .positiveColorRes(R.color.colorPrimaryDark)
                    .negativeColorRes(R.color.colorPrimaryDark)
                    .positiveText("Ye, sure!")
                    .negativeText("No, i'm not")
                    .onNegative((dialog, which) -> item.setChecked(false))
                    .onPositive((dialog, which) -> {
                        viewFlipper.setDisplayedChild(2);
                        // TODO FIX THIS
                        setupRecyclerAddAd();
                    })
                    .show();
        } else if (id == R.id.nav_add_order) {
            new MaterialDialog.Builder(this)
                    .title("ADMIN FUNCTION")
                    .content("WAIT, THIS FUNCTION IS ONLY AVAILABLE IF YOU ARE THE ADMIN! ARE YOU?")
                    .positiveColorRes(R.color.colorPrimaryDark)
                    .negativeColorRes(R.color.colorPrimaryDark)
                    .positiveText("Ye, sure!")
                    .negativeText("No, i'm not")
                    .onNegative((dialog, which) -> item.setChecked(false))
                    .onPositive((dialog, which) -> {
                        viewFlipper.setDisplayedChild(3);
                        // TODO FIX THIS
                        setupRecyclerAddAd();
                    })
                    .show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupRecyclerAddAd() {
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

    private void setupContentShops() {
        // TEST SHOPS
        mShopDao.createShop("SILPO");
        mShopDao.createShop("ATB");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 20);
        Date startDate1 = cal.getTime();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        Date endDate1 = cal.getTime();

        // TEST DIMENSIONS
        mDimensionDAO.createDimension(DimensionType.LARGE);
        mDimensionDAO.createDimension(DimensionType.SMALL);

        // TEST SPACE TYPES
        mSpaceDAO.createSpace(SpaceType.WALL);
        mSpaceDAO.createSpace(SpaceType.STAND);

        // TEST MATERIALS
        mMaterialDAO.createMaterial(MaterialType.PAPER);
        mMaterialDAO.createMaterial(MaterialType.METAL);

        // TEST SPACES
        mSpacesForAdsDAO.createSpaceForAd(1L, 1L, 1L, 1L);
        mSpacesForAdsDAO.createSpaceForAd(1L, 1L, 2L, 1L);
        mSpacesForAdsDAO.createSpaceForAd(1L, 2L, 1L, 1L);

        /*// TEST ORDERS
        mOrderDAO.createOrder(1L,1L,1L,1L,1L,1L,200L,400L);
        mOrderDAO.createOrder(2L,1L,2L,2L,2L,1L,400L,600L);*/

        //Dimension.LARGE, MaterialType.PAPER, DesignType.EXPENSIVE)
        //Dimension.LARGE, MaterialType.METAL, DesignType.EXPENSIVE)


        List<Ad> addsSilpo = mAdDao.getAllAds();


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

       /* List<Ad> addsAtb = Arrays.asList(
                new Ad("FOTIUS", startDate1, endDate1, DimensionType.SMALL, MaterialType.PAPER, DesignType.CHEAP),
                new Ad("SKIDKA NA OCHKI", startDate2, endDate2, DimensionType.SMALL, MaterialType.METAL, DesignType.EXPENSIVE),
                new Ad("Summer festival ad", startDate2, endDate2, DimensionType.SMALL, MaterialType.PAPER, DesignType.CHEAP),
                new Ad("NEW iPHONE", startDate3, endDate3, DimensionType.SMALL, MaterialType.METAL, DesignType.CHEAP)
        );*/
/*        List<SpaceForAds> spacesForAdsAtb = Arrays.asList(
                new SpaceForAds(SpaceType.WALL, DimensionType.SMALL),
                new SpaceForAds(SpaceType.WALL, DimensionType.SMALL),
                new SpaceForAds(SpaceType.WALL, DimensionType.SMALL)
        );

        shops.addAll(Arrays.asList(
                new Shop("Silpo", addsSilpo, spaceForAdsSilpo),
                new Shop("ATB", addsAtb, spacesForAdsAtb)
        ));*/
        setupRecyclerShops();
    }

    private void setupRecyclerShops() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText("Shops List");
        ShopAdapter mAdapter = new ShopAdapter(this, shops, this);
        recyclerViewShops.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewShops.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewShops.getContext(),
                layoutManager.getOrientation());
        recyclerViewShops.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        if (viewFlipper.getDisplayedChild() == 0) {
            Shop chosenShop = shops.get(position);
            Intent intent = new Intent(getApplicationContext(), DetailedShopInfoActivity.class);
            intent.putExtra(DBHelper.COLUMN_SHOP_ID, chosenShop.getId());
            startActivity(intent);
        } else if (viewFlipper.getDisplayedChild() == 1) {
            // TODO Add new ad;
        }
    }
}
