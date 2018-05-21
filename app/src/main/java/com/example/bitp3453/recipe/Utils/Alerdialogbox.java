package com.example.bitp3453.recipe.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bitp3453.recipe.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by optimum2 on 10/5/18.
 */

public class Alerdialogbox {

    Dialog dialog;
    DatabaseReference databaseReference;
    String Rating;

    public void ShowDialog(final Activity activity, final String Idd, final String Name, final String Userid) {

        dialog = new Dialog(activity, R.style.MyAlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.rating_alret);
        RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.rating);
        final EditText view_rating = (EditText) dialog.findViewById(R.id.view_rating);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        ImageView close = (ImageView) dialog.findViewById(R.id.close);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Rating = String.valueOf(rating);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ViewRating = view_rating.getText().toString();
                if (view_rating.getText().toString().equals("")) {
                    Toast.makeText(activity, "Give Rating", Toast.LENGTH_SHORT).show();

                } else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("RecipeDescription").child(Idd).child("Review");
                    String reviewID = databaseReference.push().getKey();
                    databaseReference.child(Userid).child("Review").setValue(ViewRating).toString();
                    databaseReference.child(Userid).child("Rating").setValue(Rating).toString();
                    databaseReference.child(Userid).child("Uid").setValue(reviewID).toString();

                    dialog.dismiss();
                }


            }
        });
        dialog.show();

    }


}
