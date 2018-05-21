package com.example.bitp3453.recipe.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Search.SearchActivity;
import com.example.bitp3453.recipe.Utils.Alerdialogbox;
import com.example.bitp3453.recipe.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by optimum2 on 3/5/18.
 */

public class Searching_Adapter extends RecyclerView.Adapter<Searching_Adapter.ViewHolder> {

    private ArrayList<Recipe> items = new ArrayList<>();
    SearchActivity context;
    Alerdialogbox alerdialogbox;
    String user_id;
    private List<Recipe> contactListFiltered;
    private ContactsAdapterListener listener;


    public Searching_Adapter(ArrayList<Recipe> items, SearchActivity context, ContactsAdapterListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
        this.contactListFiltered = items;

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.timelineadapter, null);
        return new ViewHolder(vv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe item = contactListFiltered.get(position);
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
        return contactListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView timelineimage;
        TextView Title, category, Description, Step, like, textshare, textcomment, text_des;
        LinearLayout click, stp_lay, ind_lay, des_layout;
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });



        }

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = items;
                } else {
                    List<Recipe> filteredList = new ArrayList<>();
                    for (Recipe row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCategory().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Recipe>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Recipe contact);
    }

}
