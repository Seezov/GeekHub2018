package com.example.android.geekhub.activities;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Festival;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailedFestivalInfoActivity extends AppCompatActivity {

    @BindView(R.id.txt_fest_name)
    TextView festName;

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
        festName.setText(festival.getName());
        ((TextView) actionBar.getCustomView().findViewById(R.id.txt_title)).setText(festival.getName());
    }
}
