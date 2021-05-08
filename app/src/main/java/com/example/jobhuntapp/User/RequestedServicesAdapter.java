package com.example.jobhuntapp.User;

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

public class RequestedServicesAdapter extends RecyclerView.Adapter<RequestedServicesAdapter.AcceptedRequestViewHolder> {

    ArrayList<RequestedServicesHelperClasses> acceptedRequest;
    private Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RequestedServicesAdapter(ArrayList<RequestedServicesHelperClasses> acceptedRequest, Context context) {
        this.acceptedRequest = acceptedRequest;
        this.context = context;

    }

    @NonNull
    @Override
    public RequestedServicesAdapter.AcceptedRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_service_providers_lists, parent, false);
        RequestedServicesAdapter.AcceptedRequestViewHolder acceptedRequestViewHolder = new RequestedServicesAdapter.AcceptedRequestViewHolder(view, mListener);
        return acceptedRequestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedServicesAdapter.AcceptedRequestViewHolder holder, int position) {

        RequestedServicesHelperClasses acceptedRequestHelperClass = acceptedRequest.get(position);

        Glide.with(context)
                .load(acceptedRequestHelperClass.getService_img())
                .into(holder.img);
        holder.u_name.setText(acceptedRequestHelperClass.getServicename());
        holder.timestamp.setText(acceptedRequestHelperClass.getTimestamp());
        holder.category.setText(acceptedRequestHelperClass.getCategory());
        holder.location.setText(acceptedRequestHelperClass.getLocation());
    }

    @Override
    public int getItemCount() {
        return acceptedRequest.size();
    }

    public static class AcceptedRequestViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView u_name, timestamp, category, location;

        public AcceptedRequestViewHolder(@NonNull View itemView, final RequestedServicesAdapter.OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.service_img);
            u_name = itemView.findViewById(R.id.service_name);
            timestamp = itemView.findViewById(R.id.service_timestamp);
            category = itemView.findViewById(R.id.service_category);
            location = itemView.findViewById(R.id.service_location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}