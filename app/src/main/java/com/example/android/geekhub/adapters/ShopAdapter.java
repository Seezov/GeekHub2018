package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.dao.AdsInShopsDAO;
import com.example.android.geekhub.dao.DimensionDAO;
import com.example.android.geekhub.dao.SpacesForAdsDAO;
import com.example.android.geekhub.entities.Shop;
import com.example.android.geekhub.entities.SpaceForAds;
import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private Context context;
    private List<Shop> shops;
    private SpacesForAdsDAO mSpacesForAdsDAO;
    private AdsInShopsDAO mAdsInShopsDAO;
    private DimensionDAO mDimensionDAO;
    private static RecyclerViewClickListener itemListener;

    public ShopAdapter(Context context, List<Shop> shops, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.shops = shops;
        mSpacesForAdsDAO = new SpacesForAdsDAO(context);
        mAdsInShopsDAO = new AdsInShopsDAO(context);
        mDimensionDAO = new DimensionDAO(context);
        ShopAdapter.itemListener = itemListener;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_shop, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.getTxtTitle().setText(shop.getName().toUpperCase());
        holder.getTxtNumberOfAds().setText(String.valueOf(mAdsInShopsDAO.getNumberOfAdsInShop(shop.getId())));
        holder.getTxtSpaceForAds().setText(String.valueOf(getSpacesString(mSpacesForAdsDAO.getSpacesInShop(shop.getId()))));
    }

    @Override
    public int getItemCount() {
        return shops.size();
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

    class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_number_of_ads) TextView txtNumberOfAds;
        @BindView(R.id.txt_space_for_ads) TextView txtSpaceForAds;

        public ShopViewHolder(View itemView) {
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
