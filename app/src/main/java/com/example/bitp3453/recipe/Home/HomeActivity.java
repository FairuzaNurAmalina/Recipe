package com.example.bitp3453.recipe.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.bitp3453.recipe.Adapter.Timeline;
import com.example.bitp3453.recipe.Login.LoginActivity;
import com.example.bitp3453.recipe.Utils.BottomNavigationViewHelper;
import com.example.bitp3453.recipe.Utils.UniversalImageLoader;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;



public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = HomeActivity.this;
    String user_id;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;


    RecyclerView recyclerView;
    Timeline timeline;
    ArrayList<Recipe> recipes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        Log.d(TAG, "onCreate: starting.");
        SharedPreferences preferences = getSharedPreferences("Credential", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "");



        setupFirebaseAuth();

        initImageLoader();

        setupBottomNavigationView();
        getname();
        GetTImeLine();

        timeline = new Timeline(recipes, HomeActivity.this, user_id);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(timeline);

    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }


    //Bottom Navigation Setup

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }


    /* --------------------------- Firebase ----------------------------------- */


    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if (user == null) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Setup the firebase auth object

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        //mViewPager.setCurrentItem(HOME_FRAGMENT);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
                timeline.notifyDataSetChanged();



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
                            recipe.setRating(""+rating_avg);


                            Log.e("pos", "" + total + "......" + rating_avg);

                        }

                        recipes.add(recipe);
                        timeline.notifyDataSetChanged();
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



}


