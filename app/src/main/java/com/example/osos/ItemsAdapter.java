package com.example.osos;

import android.annotation.SuppressLint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsViewHolder> {

    ArrayList<ItemsModelClass> list;
    Context context;
    ImageAdapter imageAdapter;
    public OnBindCallback onBind;

    public ItemsAdapter(ArrayList<ItemsModelClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public itemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_view,parent,false);
        return new itemsViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull itemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (onBind != null) {
            onBind.onViewBound(holder, position,imageAdapter);
        }
        holder.title.setText(list.get(position).getTitle());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.imageRecycler.setLayoutManager(gridLayoutManager);
        imageAdapter =  new ImageAdapter(list.get(position).urisList,context);
        holder.imageRecycler.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class itemsViewHolder extends RecyclerView.ViewHolder{
        TextView title ;
        ImageButton selectImages;
        RecyclerView imageRecycler;

        public itemsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            selectImages = itemView.findViewById(R.id.select_images);
            imageRecycler = itemView.findViewById(R.id.images_recycler);
        }
    }


}
