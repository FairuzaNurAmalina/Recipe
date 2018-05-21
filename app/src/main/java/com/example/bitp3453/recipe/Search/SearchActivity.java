package com.example.bitp3453.recipe.Search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.bitp3453.recipe.Adapter.Searching_Adapter;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Recipes.RecepyDetail;
import com.example.bitp3453.recipe.Utils.BottomNavigationViewHelper;
import com.example.bitp3453.recipe.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements Searching_Adapter.ContactsAdapterListener {

    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = SearchActivity.this;
    RecyclerView recyclerView;
    SearchView searchView;
    Searching_Adapter adapter;
    String user_id;
    ArrayList<Recipe> recipes = new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //setupBottomNavigationView();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_search);
        searchView = (SearchView) findViewById(R.id.searchView);
        SharedPreferences preferences = getSharedPreferences("Credential", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "");

        getname();


        GetTImeLine();
        adapter = new Searching_Adapter(recipes, SearchActivity.this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

        });


    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }


    public void GetTImeLine() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query qq = databaseReference.child("RecipeDescription");
        final Query qq1 = databaseReference.child("RecipeDescription").child("Review");
        qq.keepSynced(true);
        qq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes.clear();
                adapter.notifyDataSetChanged();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Recipe recipe = new Recipe();
                    try {
                        String Category = dataSnapshot1.child("Category").getValue(String.class).toString();
                        String Ingredient = dataSnapshot1.child("Ingredient").getValue(String.class).toString();
                        String ID = dataSnapshot1.child("ID").getValue(String.class).toString();
                        String RecepiImage = dataSnapshot1.child("RecepiImage").getValue(String.class).toString();
                        String Step = dataSnapshot1.child("Step").getValue(String.class).toString();
                        String Title = dataSnapshot1.child("Title").getValue(String.class).toString();
                        String unique_recipe_id = dataSnapshot1.child("unique_recipe_id").getValue(String.class).toString();
                        String Description = dataSnapshot1.child("Description").getValue(String.class).toString();

                        recipe.setCategory(Category);
                        recipe.setIngredient(Ingredient);
                        recipe.setPhoto(RecepiImage);
                        recipe.setSteps(Step);
                        recipe.setTitle(Title);
                        recipe.setID(ID);
                        recipe.setDescription(Description);
                        recipe.setRecepiuid(unique_recipe_id);

                        Iterable<DataSnapshot> ss = dataSnapshot1.child("Review").getChildren();
                        {
                            int pos = 0;
                            float total = 0;
                            float rating_avg = 0;
                            for (DataSnapshot val : ss) {
                                pos++;
                                float total_rating = Float.parseFloat(val.child("Rating").getValue().toString().toString());
                                total = total + total_rating;
                                rating_avg = total / pos;

                            }
                            recipe.setRating("" + rating_avg);


                            Log.e("pos", "" + total + "......" + rating_avg);

                        }

                        recipes.add(recipe);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }

                    qq1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    private void getname() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users)).child(user_id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences preferences = getSharedPreferences("Credential", MODE_PRIVATE);
                SharedPreferences.Editor sed = preferences.edit();
                try {
                    sed.putString("username", dataSnapshot.child("username").getValue(String.class).toString());

                    sed.commit();
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onContactSelected(Recipe contact) {


        Intent in = new Intent(SearchActivity.this, RecepyDetail.class);
        SharedPreferences sp = getSharedPreferences("Details", MODE_PRIVATE);
        SharedPreferences.Editor sed = sp.edit();
        sed.putString("RecepiImage", contact.getPhoto());
        sed.putString("Title", contact.getTitle());
        sed.putString("Category", contact.getCategory());
        sed.putString("Description", contact.getDescription());
        sed.putString("Ingredient", contact.getIngredient());
        sed.putString("Step", contact.getSteps());
        sed.putString("ID", contact.getID());
        sed.putString("unique_recipe_id", contact.getRecepiuid());
        sed.putString("Rating", contact.getRating());
        sed.commit();
        startActivity(in);

    }
}
