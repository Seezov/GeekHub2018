package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Ad;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.BandViewHolder> {

    private Context context;
    private List<Ad> ads;
    private static RecyclerViewClickListener itemListener;

    public AdAdapter(Context context, List<Ad> ads) {
        this.context = context;
        this.ads = ads;
       // AdAdapter.itemListener = itemListener;
    }

    @Override
    public BandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_ad, parent, false);
        return new BandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BandViewHolder holder, int position) {
        Ad ad = ads.get(position);
        holder.getTxtTitle().setText(ad.getName());
        holder.getTxtDate().setText(ad.getDateString());
        holder.getTxtDesign().setText(ad.getDesignString());
        holder.getTxtMaterial().setText(ad.getMaterial());
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    class BandViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_date) TextView txtDate;
        @BindView(R.id.txt_design) TextView txtDesign;
        @BindView(R.id.txt_material) TextView txtMaterial;

        public BandViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtDesign() {
            return txtDesign;
        }

        public TextView getTxtMaterial() {
            return txtMaterial;
        }
    }
}
