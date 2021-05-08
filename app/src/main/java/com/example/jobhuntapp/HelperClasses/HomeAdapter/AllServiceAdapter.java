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
import com.example.jobhuntapp.HelperClasses.ServiceProviderViewAdapter;
import com.example.jobhuntapp.HelperClasses.ServiceProvidersView;
import com.example.jobhuntapp.R;

import java.util.ArrayList;

public class AllServiceAdapter extends RecyclerView.Adapter<AllServiceAdapter.AllServiceViewHolder> {

    ArrayList<AllServiceClass> allServices;
    private Context context;

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public AllServiceAdapter(ArrayList<AllServiceClass> allServices,Context context) {
        this.allServices = allServices;
        this.context = context;

    }

    @NonNull
    @Override
    public AllServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_services_cards,parent,false);
        AllServiceViewHolder allServiceViewHolder = new AllServiceViewHolder(view,mListener);
        return allServiceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllServiceViewHolder holder, int position) {

        AllServiceClass allServiceClass = allServices.get(position);

        Glide.with(context)
                .load(allServiceClass.getImage())
                .into(holder.img);
        holder.full_name.setText(allServiceClass.getFull_name());
        holder.desc.setText(allServiceClass.getDescription());
        holder.category.setText(allServiceClass.getCategory());
        holder.location.setText(allServiceClass.getLocation());
    }
    @Override
    public int getItemCount() {
        return allServices.size();
    }

    public static class AllServiceViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView full_name,desc,category,location;

        public AllServiceViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
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
