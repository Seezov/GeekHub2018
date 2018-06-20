package com.example.android.geekhub.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.geekhub.R;
import com.example.android.geekhub.adapters.AdAdapter;
import com.example.android.geekhub.dao.AdsInShopsDAO;
import com.example.android.geekhub.dao.DimensionDAO;
import com.example.android.geekhub.dao.ShopDAO;
import com.example.android.geekhub.dao.SpacesForAdsDAO;
import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Dimension;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.entities.SpaceForAds;
import com.example.android.geekhub.enums.DimensionType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedShopInfoActivity extends AppCompatActivity {

    @BindView(R.id.txt_number_of_ads)
    TextView txtNumOfAds;
    @BindView(R.id.txt_spaces)
    TextView txtSpaces;
    @BindView(R.id.recycler_view_ads)
    RecyclerView recyclerViewAds;
    @BindView(R.id.view_scroll)
    ScrollView viewScroll;

    ShopDAO mShopDAO;
    AdsInShopsDAO mAdsInShopsDAO;
    SpacesForAdsDAO mSpacesForAdsDAO;
    DimensionDAO mDimensionDAO;
    ActionBar actionBar;
    Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_shop_info);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        mShopDAO = new ShopDAO(this);
        mAdsInShopsDAO = new AdsInShopsDAO(this);
        mSpacesForAdsDAO = new SpacesForAdsDAO(this);
        mDimensionDAO = new DimensionDAO(this);
        Bundle data = getIntent().getExtras();
        shop = mShopDAO.getShopById(data.getLong(DBHelper.COLUMN_SHOP_ID));

        setupActionBar();
        setupContent();
        viewScroll.smoothScrollBy(0,0);

        findViewById(R.id.img_help).setOnClickListener(view ->
                Toast.makeText(DetailedShopInfoActivity.this, "Here you can see a detailed info about " + shop.getName(),Toast.LENGTH_SHORT).show());
    }

    public void setupActionBar() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bg_toolbar));
        actionBar.setCustomView(R.layout.actionbar_default);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    private void setupContent() {
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText(shop.getName());
        txtNumOfAds.setText(String.valueOf(mAdsInShopsDAO.getNumberOfAdsInShop(shop.getId())));
        txtSpaces.setText(getSpacesString(mSpacesForAdsDAO.getSpacesInShop(shop.getId())));
        setupRecycler();
    }

    private void setupRecycler() {
        AdAdapter mAdapter = new AdAdapter(this, mAdsInShopsDAO.geAdsInShop(shop.getId()));
        recyclerViewAds.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAds.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAds.getContext(),
                layoutManager.getOrientation());
        recyclerViewAds.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public String getSpacesString(List<SpaceForAds> spacesForAds) {
        if (spacesForAds.isEmpty()) {
            return "There are no spaces for ads";
        }
        int largeSpaces = 0;
        int smallSpaces = 0;
        for (SpaceForAds space: spacesForAds) {
            String currentDimension = mDimensionDAO.getDimensionById(space.getIdDimension()).getName();
            if (currentDimension.equals(DimensionType.LARGE) ){
                largeSpaces++;
            } else if (currentDimension.equals(DimensionType.SMALL)) {
                smallSpaces++;
            }
        }
        return "There are " + largeSpaces + " large spaces and " + smallSpaces + " small spaces.";
    }
}
