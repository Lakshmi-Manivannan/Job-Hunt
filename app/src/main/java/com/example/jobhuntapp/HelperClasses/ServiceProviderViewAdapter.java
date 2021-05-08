package com.example.jobhuntapp.HelperClasses;

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

public class ServiceProviderViewAdapter extends RecyclerView.Adapter<ServiceProviderViewAdapter.ServiceProviderViewHolder>{

    ArrayList<ServiceProvidersView> serviceProviders;
    private Context context;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public ServiceProviderViewAdapter(ArrayList<ServiceProvidersView> serviceProviders, Context context) {
        this.serviceProviders = serviceProviders;
        this.context = context;

    }
    @NonNull
    @Override
    public ServiceProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_service_providers_lists,parent,false);
        ServiceProviderViewAdapter.ServiceProviderViewHolder serviceProviderViewHolder = new ServiceProviderViewAdapter.ServiceProviderViewHolder(view,mListener);
        return serviceProviderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderViewHolder holder, int position) {
        ServiceProvidersView serviceProvidersView = serviceProviders.get(position);



        Glide.with(context)
                .load(serviceProvidersView.getImage())
                .into(holder.img);

        holder.full_name.setText(serviceProvidersView.getFull_name());
        holder.desc.setText(serviceProvidersView.getDescription());
        holder.category.setText(serviceProvidersView.getCategory());
        holder.location.setText(serviceProvidersView.getLocation());
    }

    @Override
    public int getItemCount() {
        return serviceProviders.size();    }

    public static class ServiceProviderViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView full_name,desc,category,location;
        public ServiceProviderViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.service_img);
            full_name = itemView.findViewById(R.id.service_name);
            desc = itemView.findViewById(R.id.service_timestamp);
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
