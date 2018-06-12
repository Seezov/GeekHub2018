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
import com.example.android.geekhub.entities.Shop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    ActionBar actionBar;
    Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_shop_info);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();

        Bundle data = getIntent().getExtras();
        shop = data.getParcelable("shop");

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
/*        txtNumOfAds.setText(String.valueOf(shop.getAds().size()));
        txtSpaces.setText(shop.getSpacesString());*/
        setupRecycler();
    }

    private void setupRecycler() {
        /*AdAdapter mAdapter = new AdAdapter(this, shop.getAds());
        recyclerViewAds.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewAds.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAds.getContext(),
                layoutManager.getOrientation());
        recyclerViewAds.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
