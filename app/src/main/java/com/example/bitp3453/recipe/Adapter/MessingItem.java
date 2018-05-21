package com.example.bitp3453.recipe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.models.Messaging;

import java.util.ArrayList;

/**
 * Created by optimum2 on 4/5/18.
 */

public class MessingItem extends RecyclerView.Adapter<MessingItem.ViewHolder> {

    ArrayList<Messaging> messingItems = new ArrayList<>();
    Context context;


    public MessingItem(ArrayList<Messaging> messingItems, Context context) {
        this.messingItems = messingItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.messaging, null);

        return new ViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Messaging Mi = messingItems.get(position);
        holder.name.setText(Mi.getName()+":");
        holder.user_message.setText(Mi.getMessage());

        Log.e("Datata===", Mi.getName());

    }

    @Override
    public int getItemCount() {
        return messingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, user_message;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.user_name);
            user_message = (TextView) itemView.findViewById(R.id.user_message);

        }
    }
}
