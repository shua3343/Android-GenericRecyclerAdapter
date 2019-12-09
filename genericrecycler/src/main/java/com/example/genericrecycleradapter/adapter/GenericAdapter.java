package com.example.genericrecycleradapter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenericAdapter<T> extends GenericAdapterConfiguration<T> {

    public GenericAdapter(@LayoutRes int itemView, List<T> contentObjects) {
        super(itemView, contentObjects);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(getItemView(),
                        parent,
                        false);

        if (missingIds()) {
            try {
                configureIds((ViewGroup) view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new GenericAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        configureLayout(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return getContentObjectsSize();
    }

    public class GenericAdapterViewHolder extends RecyclerView.ViewHolder {
        GenericAdapterViewHolder(View view) {
            super(view);
        }
    }
}
