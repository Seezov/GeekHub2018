package com.example.android.geekhub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.adapters.BandAdapter;
import com.example.android.geekhub.entities.Band;
import com.example.android.geekhub.entities.Festival;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedFestivalInfoActivity extends AppCompatActivity implements RecyclerViewClickListener {

    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_active)
    TextView txtActive;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.img_activity)
    ImageView imgActivity;
    @BindView(R.id.recycler_view_bands)
    RecyclerView recyclerViewBands;

    ActionBar actionBar;
    Festival festival;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_festival_info);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();

        Bundle data = getIntent().getExtras();
        festival = data.getParcelable("festival");

        setupActionBar();
        setupContent();
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
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText(festival.getName());
        txtDate.setText(getDateString(festival));
        if (festival.isActive()) {
            txtActive.setText("This event will be held in the future");
            imgActivity.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fest_active));
        } else {
            txtActive.setText("This event is over");
            imgActivity.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fest_inactive));
        }
        txtLocation.setText(festival.getLocation());
        txtPrice.setText(String.valueOf(festival.getPrice()));
        setupRecycler();
    }

    private void setupRecycler() {
        BandAdapter mAdapter = new BandAdapter(this, festival.getBands(), this);
        recyclerViewBands.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewBands.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewBands.getContext(),
                layoutManager.getOrientation());
        recyclerViewBands.addItemDecoration(dividerItemDecoration);
        mAdapter.notifyDataSetChanged();
    }

    private String getDateString(Festival fest) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = fest.getStartDate();
        Date endDate = fest.getEndDate();
        return df.format(startDate) + " - " + df.format(endDate);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Band chosenBand = festival.getBands().get(position);
        Intent intent = new Intent(getApplicationContext(), DetailedBandInfoActivity.class);
        intent.putExtra("band", chosenBand);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
