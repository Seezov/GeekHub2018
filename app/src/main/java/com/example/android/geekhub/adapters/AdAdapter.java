package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.dao.DesignDAO;
import com.example.android.geekhub.dao.DimensionDAO;
import com.example.android.geekhub.dao.MaterialDAO;
import com.example.android.geekhub.dao.OrderDAO;
import com.example.android.geekhub.entities.Ad;
import com.example.android.geekhub.entities.Design;
import com.example.android.geekhub.entities.Material;
import com.example.android.geekhub.entities.Order;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        holder.getTxtDate().setText(getDateString(ad));
        holder.getTxtDesign().setText(getDesignString(ad));
        holder.getTxtMaterial().setText(getMaterialString(ad));
    }

    private String getMaterialString(Ad ad) {
        MaterialDAO mMaterialDAO = new MaterialDAO(context);
        OrderDAO mOrderDAO = new OrderDAO(context);
        List<Order> listOrders = mOrderDAO.getOrdersByAd(ad.getId());
        List<Material> listMaterials = new ArrayList<>();
        for (Order order: listOrders) {
            listMaterials.add(mMaterialDAO.getMaterialById(order.getIdMaterialType()));
        }
        if (listMaterials.size() > 1) {
            return getMaterialStringMultiple(listMaterials);
        } else {
            return getMaterialStringSingle(listMaterials);
        }
    }

    private String getMaterialStringSingle(List<Material> listMaterials) {
        return "There is 1 material: " + listMaterials.get(0).getName();
    }

    private String getMaterialStringMultiple(List<Material> listMaterials) {
        StringBuilder result = new StringBuilder("There is " + listMaterials.size() + " material: ");
        for (Material material: listMaterials) {
            result.append(material.getName()).append(" ");
        }
        return result.toString();
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public String getDateString(Ad ad) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(ad.getStartDate()) + " - " + df.format(ad.getEndDate());
    }

    public String getDesignString(Ad ad) {
        DesignDAO mDesignDAO = new DesignDAO(context);
        DimensionDAO mDimensionDAO = new DimensionDAO(context);
        Design currentDesign = mDesignDAO.getDesignByAd(ad.getId());
        String designType = currentDesign.getName();
        String dimension = mDimensionDAO.getDimensionById(currentDesign.getIdDimension()).getName();
        return "Design name is " + designType + " and it's dimension is " + dimension;
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
