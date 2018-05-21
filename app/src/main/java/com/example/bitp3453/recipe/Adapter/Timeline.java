package com.example.bitp3453.recipe.Adapter;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitp3453.recipe.Home.HomeActivity;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Recipes.RecepyDetail;
import com.example.bitp3453.recipe.Utils.Alerdialogbox;
import com.example.bitp3453.recipe.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by optimum2 on 3/5/18.
 */

public class Timeline extends RecyclerView.Adapter<Timeline.ViewHolder> {

    private ArrayList<Recipe> items = new ArrayList<>();
    HomeActivity context;
    Alerdialogbox alerdialogbox;
    String user_id;

    public Timeline(ArrayList<Recipe> items, HomeActivity context, String user_id) {
        this.items = items;
        this.context = context;
        this.user_id = user_id;
    }


    public Timeline(ArrayList<Recipe> items, HomeActivity context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.timelineadapter, null);
        return new ViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe item = items.get(position);
        holder.Title.setText(item.getTitle());
        holder.Description.setText(item.getIngredient());
        holder.Step.setText(item.getSteps());
        holder.text_des.setText(item.getDescription());
        holder.category.setText(item.getCategory());
        Picasso.with(context).load(item.getPhoto()).placeholder(R.drawable.placeholder).resize(80, 80).into(holder.timelineimage);
        holder.itemView.setTag(item);
        holder.ratingview.setRating(Float.parseFloat(item.getRating()));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView timelineimage;
        TextView Title, category, Description, Step, like, textshare, textcomment,text_des;
        LinearLayout click, stp_lay, ind_lay,des_layout;
        RatingBar ratingview;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            Description = (TextView) itemView.findViewById(R.id.textdescription);
            like = (TextView) itemView.findViewById(R.id.textlike);
            timelineimage = (CircleImageView) itemView.findViewById(R.id.timelineimage);
            Step = (TextView) itemView.findViewById(R.id.steps);
            textshare = (TextView) itemView.findViewById(R.id.textshare);
            textcomment = (TextView) itemView.findViewById(R.id.textcomment);
            text_des = (TextView) itemView.findViewById(R.id.text_des);
            click = (LinearLayout) itemView.findViewById(R.id.click);
            ratingview = (RatingBar) itemView.findViewById(R.id.ratingview);
            stp_lay = (LinearLayout) itemView.findViewById(R.id.stp_lay);
            ind_lay = (LinearLayout) itemView.findViewById(R.id.ind_lay);
            des_layout = (LinearLayout) itemView.findViewById(R.id.des_layout);
            stp_lay.setVisibility(View.GONE);
            ind_lay.setVisibility(View.GONE);



            textshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Recipe recipe1 = items.get(position);
                    Log.e("Datatat", recipe1.getCategory() + "........." + recipe1.getDescription()+"  "+recipe1.getTitle());
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = recipe1.getTitle()+"\n"+recipe1.getCategory() + "\n" + recipe1.getIngredient()+"\n"+recipe1.getSteps()+"\n"+recipe1.getDescription();
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

                }
            });


            textcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Recipe recipe = items.get(position);
                    Intent in = new Intent(context, RecepyDetail.class);
                    SharedPreferences sp = context.getSharedPreferences("Details", MODE_PRIVATE);
                    SharedPreferences.Editor sed = sp.edit();
                    sed.putString("RecepiImage", recipe.getPhoto());
                    sed.putString("Title", recipe.getTitle());
                    sed.putString("Category", recipe.getCategory());
                    sed.putString("Description", recipe.getDescription());
                    sed.putString("Ingredient", recipe.getIngredient());
                    sed.putString("Step", recipe.getSteps());
                    sed.putString("ID", recipe.getID());
                    sed.putString("unique_recipe_id", recipe.getRecepiuid());
                    sed.putString("Rating", recipe.getRating());
                    sed.commit();
                    context.startActivity(in);
                }
            });
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Recipe recipe = items.get(position);
                    Intent in = new Intent(context, RecepyDetail.class);
                    SharedPreferences sp = context.getSharedPreferences("Details", MODE_PRIVATE);
                    SharedPreferences.Editor sed = sp.edit();
                    sed.putString("RecepiImage", recipe.getPhoto());
                    sed.putString("Title", recipe.getTitle());
                    sed.putString("Category", recipe.getCategory());
                    sed.putString("Description", recipe.getDescription());
                    sed.putString("Ingredient", recipe.getIngredient());
                    sed.putString("Step", recipe.getSteps());
                    sed.putString("ID", recipe.getID());
                    sed.putString("unique_recipe_id", recipe.getRecepiuid());
                    sed.putString("Rating", recipe.getRating());
                    sed.commit();
                    context.startActivity(in);
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alerdialogbox = new Alerdialogbox();
                    int position = getAdapterPosition();
                    Recipe recipe = items.get(position);
                    Toast.makeText(context, "" + recipe.getRecepiuid(), Toast.LENGTH_SHORT).show();
                    alerdialogbox.ShowDialog(context, recipe.getRecepiuid(), recipe.getUsername(), user_id);

                }
            });
        }

    }


}
