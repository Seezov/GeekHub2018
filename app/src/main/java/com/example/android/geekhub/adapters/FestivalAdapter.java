package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Festival;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.FestivalViewHolder> {

    private Context context;
    private List<Festival> festivals;
    private static RecyclerViewClickListener itemListener;

    public FestivalAdapter(Context context, List<Festival> festivals,  RecyclerViewClickListener itemListener) {
        this.context = context;
        this.festivals = festivals;
        FestivalAdapter.itemListener = itemListener;
    }

    @Override
    public FestivalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_festival, parent, false);
        return new FestivalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FestivalViewHolder holder, int position) {
        Festival fest = festivals.get(position);
        holder.getTxtTitle().setText(fest.getName().toUpperCase());
        holder.getTxtLocation().setText(fest.getLocation().toUpperCase());
        holder.getTxtDate().setText(getDateString(fest));
        holder.getTxtPrice().setText(String.valueOf(fest.getPrice()));
        holder.getImgActivity().setImageDrawable(ContextCompat.getDrawable(context, fest.isActive() ? R.drawable.ic_fest_active : R.drawable.ic_fest_inactive));

    }

    private String getDateString(Festival fest) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = fest.getStartDate();
        Date endDate = fest.getEndDate();
        return df.format(startDate) + " - " + df.format(endDate);
    }

    @Override
    public int getItemCount() {
        return festivals.size();
    }

    class FestivalViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_location) TextView txtLocation;
        @BindView(R.id.txt_date)TextView txtDate;
        @BindView(R.id.txt_price) TextView txtPrice;
        @BindView(R.id.img_activity)
        ImageView imgActivity;

        public FestivalViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtLocation() {
            return txtLocation;
        }

        public TextView getTxtDate() {
            return txtDate;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }

        public ImageView getImgActivity() {
            return imgActivity;
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }
}
