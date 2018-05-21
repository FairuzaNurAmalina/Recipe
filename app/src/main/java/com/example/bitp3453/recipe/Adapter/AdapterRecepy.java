package com.example.bitp3453.recipe.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bitp3453.recipe.Recipes.RecipesActivity;
import com.example.bitp3453.recipe.Utils.Send_Recepi_ID;
import com.example.bitp3453.recipe.models.Add_recepy;
import com.example.bitp3453.recipe.R;

import java.util.ArrayList;


public class AdapterRecepy extends RecyclerView.Adapter<AdapterRecepy.ViewHolder> {

    ArrayList<Add_recepy> add_recepies;
    RecipesActivity recipesActivity;


    public AdapterRecepy(ArrayList<Add_recepy> add_recepies, RecipesActivity recipesActivity) {
        this.add_recepies = add_recepies;
        this.recipesActivity = recipesActivity;
    }

    @NonNull
    @Override
    public AdapterRecepy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
        return new ViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecepy.ViewHolder holder, final int position) {

        Add_recepy add_recepy = add_recepies.get(position);
        if (add_recepy.getBitmap() != null) {
            holder.imageView.setImageBitmap(add_recepy.getBitmap());
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        holder.textout.setText(add_recepy.getName());
        holder.remove.setTag(position);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_recepies.remove((holder.remove.getTag()));

                ((Send_Recepi_ID)recipesActivity).yourDesiredMethod(""+holder.remove.getTag());


            }
        });

    }

    @Override
    public int getItemCount() {
        return add_recepies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textout;
        ImageButton imageView;
        ImageView remove;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageButton) itemView.findViewById(R.id.Image);
            textout = (TextView) itemView.findViewById(R.id.textout);
            remove = (ImageView) itemView.findViewById(R.id.remove);


        }
    }


}
