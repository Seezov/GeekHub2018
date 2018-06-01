package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.FestivalViewHolder> {

    private Context context;
    private List<Shop> shops;
    private static RecyclerViewClickListener itemListener;

    public ShopAdapter(Context context, List<Shop> shops, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.shops = shops;
        ShopAdapter.itemListener = itemListener;
    }

    @Override
    public FestivalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_shop, parent, false);
        return new FestivalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FestivalViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.getTxtTitle().setText(shop.getName().toUpperCase());
        holder.getTxtNumberOfAds().setText(String.valueOf(shop.getAds().size()));
        holder.getTxtSpaceForAds().setText(shop.getSpacesString());
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    class FestivalViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_number_of_ads) TextView txtNumberOfAds;
        @BindView(R.id.txt_space_for_ads) TextView txtSpaceForAds;

        public FestivalViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtNumberOfAds() {
            return txtNumberOfAds;
        }

        public TextView getTxtSpaceForAds() {
            return txtSpaceForAds;
        }
        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }
}
