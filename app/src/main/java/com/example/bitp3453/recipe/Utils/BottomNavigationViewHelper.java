package com.example.bitp3453.recipe.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.bitp3453.recipe.Home.HomeActivity;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Recipes.RecipesActivity;
import com.example.bitp3453.recipe.Search.SearchActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";


    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);


    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view) {
//        SharedPreferences preferences = context.getSharedPreferences("Credential", MODE_PRIVATE);
//        final String login = preferences.getString("login", "");
//        Log.e("login", login);
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_search:
                        Intent intent2 = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_recipes:
                        Intent intent3 = new Intent(context, RecipesActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                }


                return false;
            }
        });
    }

}