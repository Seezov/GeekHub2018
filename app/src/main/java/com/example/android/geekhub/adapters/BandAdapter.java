package com.example.android.geekhub.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.geekhub.R;
import com.example.android.geekhub.entities.Band;
import com.example.android.geekhub.listeners.RecyclerViewClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BandAdapter extends RecyclerView.Adapter<BandAdapter.BandViewHolder> {

    private Context context;
    private List<Band> bands;
    private static RecyclerViewClickListener itemListener;

    public BandAdapter(Context context, List<Band> bands, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.bands = bands;
        BandAdapter.itemListener = itemListener;
    }

    @Override
    public BandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_band, parent, false);
        return new BandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BandViewHolder holder, int position) {
        Band band = bands.get(position);
        holder.getTxtTitle().setText(band.getName().toUpperCase());
        holder.getTxtGenre().setText(getGenres(band));
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
    public int getItemCount() {
        return bands.size();
    }

    class BandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_genre)
        TextView txtGenre;

        public BandViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtGenre() {
            return txtGenre;
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }
}
