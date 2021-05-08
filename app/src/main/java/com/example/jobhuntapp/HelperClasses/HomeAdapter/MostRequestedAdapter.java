package com.example.jobhuntapp.HelperClasses.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jobhuntapp.R;

import java.util.ArrayList;

public class MostRequestedAdapter extends RecyclerView.Adapter<MostRequestedAdapter.MostRequestedViewHolder> {

    ArrayList<MostRequestedClass> mostRequested;
    private Context context;

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public MostRequestedAdapter(ArrayList<MostRequestedClass> mostRequested,Context context) {
        this.mostRequested = mostRequested;
        this.context = context;

    }

    @NonNull
    @Override
    public MostRequestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_requested_cards,parent,false);
        MostRequestedAdapter.MostRequestedViewHolder mostRequestedViewHolder = new MostRequestedAdapter.MostRequestedViewHolder(view,mListener);
        return mostRequestedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostRequestedViewHolder holder, int position) {
        MostRequestedClass mostRequestedClass = mostRequested.get(position);

        Glide.with(context)
                .load(mostRequestedClass.getImage())
                .into(holder.img);
        holder.full_name.setText(mostRequestedClass.getFull_name());
        holder.desc.setText(mostRequestedClass.getDescription());
        holder.category.setText(mostRequestedClass.getCategory());
        holder.location.setText(mostRequestedClass.getLocation());
    }

    @Override
    public int getItemCount() {
        return mostRequested.size();
    }

    public static class MostRequestedViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView full_name,desc,category,location;
        public MostRequestedViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.service_img);
            full_name = itemView.findViewById(R.id.service_name);
            desc = itemView.findViewById(R.id.service_desc);
            category = itemView.findViewById(R.id.service_category);
            location = itemView.findViewById(R.id.service_location);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
