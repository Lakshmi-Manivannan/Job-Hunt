package com.example.jobhuntapp.HelperClasses.HomeAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhuntapp.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    ArrayList<CategoriesClass> categories;

    private Context context;

    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public CategoriesAdapter(ArrayList<CategoriesClass> categories,Context context) {
        this.categories = categories;
        this.context = context;

    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories,parent,false);
        CategoriesAdapter.CategoriesViewHolder categoriesViewHolder = new CategoriesAdapter.CategoriesViewHolder(view,mListener);
        return categoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoriesClass categoriesClass = categories.get(position);

        holder.img.setImageResource(categoriesClass.getImage());
        holder.title.setText(categoriesClass.getTitle());
        holder.background.setBackgroundColor(categoriesClass.getBackground());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;
        CardView background;

        public CategoriesViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.service_img);
            title = itemView.findViewById(R.id.text_view_category);
            background = itemView.findViewById(R.id.categories_view_layout);

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
