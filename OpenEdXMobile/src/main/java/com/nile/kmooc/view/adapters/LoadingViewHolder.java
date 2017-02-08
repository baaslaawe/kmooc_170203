package com.nile.kmooc.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public LoadingViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).
                inflate(com.nile.kmooc.R.layout.list_view_footer_progress, parent, false));
    }
}
