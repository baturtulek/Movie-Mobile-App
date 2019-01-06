package com.baturtulek.movie;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> movieList = new ArrayList<>();

    public RecyclerAdapter(Context mContext, ArrayList<Movie> mArrayList) {
        this.mContext = mContext;
        this.movieList = mArrayList;
    }

    // Each object of the ViewHolder will be created here
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = (View) mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(itemView);

        return mViewHolder;
    }

    // This method will be called to assign data to each row or cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Movie m = movieList.get(position);
        String title = m.getName();
        String avg = Double.toString(m.getVoteAvg());
        String path = m.getImgPath();
        String url = "https://image.tmdb.org/t/p/w500" + path;
        Picasso.get()
                .load(url)
                .into(holder.image);

        holder.name.setText(title);
        holder.avg.setText("Average : " + avg);
        }

    // How many items exist in the list
    @Override
    public int getItemCount() {
        return movieList.size();
    }


    // This class is responsible for each item on the list
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView avg;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv);
            avg = itemView.findViewById(R.id.tvAvg);
            image = itemView.findViewById(R.id.imv);
        }
    }
}