package com.example.android.geekhub.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Band;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedBandInfoActivity extends AppCompatActivity {

    @BindView(R.id.txt_genre)
    TextView txtGenre;

    ActionBar actionBar;
    Band band;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_band_info);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        Bundle data = getIntent().getExtras();
        band = data.getParcelable("band");

        setupActionBar();
        setupContent();
        findViewById(R.id.img_help).setOnClickListener(view ->
                Toast.makeText(DetailedBandInfoActivity.this, "Here you can see a detailed info about " + band.getName(),Toast.LENGTH_SHORT).show());

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
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText(band.getName());
        txtGenre.setText(getGenres(band));
    }

    private String getGenres(Band band) {
        String completeString = "";
        for (int i = 0; i < band.getGenres().size(); i++) {
            if (i != 0) {
                completeString += ", ";
            }
            completeString += band.getGenres().get(i);
        }
        return completeString;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
