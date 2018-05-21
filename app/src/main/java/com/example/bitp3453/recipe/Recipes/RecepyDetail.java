package com.example.bitp3453.recipe.Recipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bitp3453.recipe.Adapter.MessingItem;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Utils.Alerdialogbox;
import com.example.bitp3453.recipe.models.Messaging;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecepyDetail extends AppCompatActivity implements View.OnClickListener  {
    TextView Title, category, Description, like, Step,text_des;
    String Descriptionff,Title1, category1, Ingre, RecepiImage1, Step1, IDD, userID, message_text1, login, Rating;
    ImageView timelineimage;
    RecyclerView RecyclerView;
    ImageView message;
    EditText message_text;
    DatabaseReference reference, reference1;
    ArrayList<Messaging> messagings = new ArrayList<>();
    MessingItem item;
    String Message, Name, From, unique_recipe_id, username;
    StringBuilder st = new StringBuilder();
    StringBuilder des = new StringBuilder();
    LinearLayout l1, llrate, llshare;
    int pos = 1;
    int pos1 = 1;
    RatingBar ratingview;
    Alerdialogbox alerdialogbox;
    TextView textshare,textlike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepy_detail);
        Title = (TextView) findViewById(R.id.title);
        category = (TextView) findViewById(R.id.category);
        RecyclerView = (RecyclerView) findViewById(R.id.recycle);
        Description = (TextView) findViewById(R.id.textdescription);
        message_text = (EditText) findViewById(R.id.message_text);
        ratingview = (RatingBar) findViewById(R.id.ratingview);
        Description.setMaxLines(1000);
        like = (TextView) findViewById(R.id.textlike);
        timelineimage = (ImageView) findViewById(R.id.timelineimage);
        l1 = (LinearLayout) findViewById(R.id.l1);
        llrate = (LinearLayout) findViewById(R.id.llrate);
        llshare = (LinearLayout) findViewById(R.id.llshare);
        textlike = (TextView) findViewById(R.id.textlike);
        textshare = (TextView) findViewById(R.id.textshare);
        message = (ImageView) findViewById(R.id.message);
        message.setOnClickListener(this);
        Step = (TextView) findViewById(R.id.steps);
        text_des = (TextView) findViewById(R.id.text_des);
        Step.setMaxLines(1000);
        SharedPreferences sp = getSharedPreferences("Details", MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("Credential", MODE_PRIVATE);
        userID = preferences.getString("user_id", "");
        username = preferences.getString("username", "");
        login = preferences.getString("login", "");


        IDD = sp.getString("ID", "");
        RecepiImage1 = sp.getString("RecepiImage", "");
        Title1 = sp.getString("Title", "");
        category1 = sp.getString("Category", "");
        Ingre = sp.getString("Ingredient", "");
        Step1 = sp.getString("Step", "");
        unique_recipe_id = sp.getString("unique_recipe_id", "");
        Rating = sp.getString("Rating", "");
        Descriptionff = sp.getString("Description", "");

        HitAdapter();
        Title.setText(Title1);
        category.setText(category1);
        ratingview.setRating(Float.parseFloat(Rating));
        text_des.setText(Descriptionff);
        // Description.setText(Ingre);
        //Step.setText(Step1);
        Log.e("st====", "" + Ingre+"------"+Step1);


                String[] namesList = Step1.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
                int steplenght = namesList.length;
                int stepminus = 1;
                for (String name : namesList) {

                    if (steplenght - stepminus >= pos) {
                        st.append(pos++ + ":- " + name + "\n");
                    } else {

                    }
                }

            Step.setText(st);

            String[] dexList = Ingre.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
            int deslenght = dexList.length;
            int desminus = 1;
            for (String name1 : dexList) {

                if (deslenght - desminus >= pos1) {
                    des.append(pos1++ + ":- " + name1 + "\n");
                } else {

                }


            }
            Description.setText(des);
            Picasso.with(RecepyDetail.this).load(RecepiImage1).placeholder(R.drawable.placeholder).into(timelineimage);

            if (login.equals("not")) {
                l1.setVisibility(View.GONE);
            } else {

            }

            llshare.setOnClickListener(this);
            llrate.setOnClickListener(this);
            textlike.setOnClickListener(this);
            textshare.setOnClickListener(this);

        }

    @Override
    public void onClick(View view) {
        if (view == message) {
            Messaging_send();
        }else if(view == llrate)
        {
            alerdialogbox = new Alerdialogbox();
            // int position = getAdapterPosition();
            //Recipe recipe = items.get(position);
            Toast.makeText(getApplicationContext(), "" + unique_recipe_id, Toast.LENGTH_SHORT).show();
            alerdialogbox.ShowDialog(RecepyDetail.this, unique_recipe_id, username, userID);

        }else if(view == llshare)
        {
//            int position = getAdapterPosition();
//            Recipe recipe1 = items.get(position);
            Log.e("Datatat", category1 + "........." + Ingre+"  "+Title1);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody = Title1+"\n"+category1 + "\n" + Ingre+"\n"+Step1+"\n"+Descriptionff;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }else if(view == textlike)
        {
            alerdialogbox = new Alerdialogbox();
            // int position = getAdapterPosition();
            //Recipe recipe = items.get(position);
            Toast.makeText(getApplicationContext(), "" + unique_recipe_id, Toast.LENGTH_SHORT).show();
            alerdialogbox.ShowDialog(RecepyDetail.this, unique_recipe_id, username, userID);

        }else if(view == textshare)
        {
//            int position = getAdapterPosition();
//            Recipe recipe1 = items.get(position);
            Log.e("Datatat", category1 + "........." + Ingre+"  "+Title1);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody = Title1+"\n"+category1 + "\n" + Ingre+"\n"+Step1+"\n"+Descriptionff;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }

    }


    private void Messaging_send() {
        message_text = (EditText) findViewById(R.id.message_text);
        message_text1 = message_text.getText().toString();
        if (message_text.getText().toString().equals("")) {
            message_text.setError("Fill Message");
        } else {
            reference = FirebaseDatabase.getInstance().getReference("Messageing");
            String uid = reference.push().getKey();
            reference.child(unique_recipe_id).child(uid).child("Message").setValue(message_text1).toString();
            reference.child(unique_recipe_id).child(uid).child("Name").setValue(username).toString();
            reference.child(unique_recipe_id).child(uid).child("From").setValue(userID).toString();
            Toast.makeText(this, "Uploded", Toast.LENGTH_SHORT).show();
            message_text.setText("");
            HitAdapter();
        }
    }


    private void GetMessage_chat() {
        reference1 = FirebaseDatabase.getInstance().getReference();
        Query query = reference1.child("Messageing").child(unique_recipe_id);
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("databaseError==", "" + dataSnapshot.getChildren() + "..." + dataSnapshot.getKey());
                messagings.clear();
                item.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Messaging messaging = new Messaging();
                    try {
                        From = dataSnapshot1.child("From").getValue(String.class).toString();
                        Message = dataSnapshot1.child("Message").getValue(String.class).toString();
                        Name = dataSnapshot1.child("Name").getValue(String.class).toString();
                        Log.e("Message==", Message);
                        messaging.setMessage(Message);
                        messaging.setName(Name);
                        messaging.setFromID(From);
                        messagings.add(messaging);
                        item.notifyDataSetChanged();
                    } catch (Exception e) { 
                        Log.e("databaseError==", e.toString());
                    }
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("databaseError==", databaseError.toString());
            }
        });
    }


    private void HitAdapter() {
        GetMessage_chat();
        item = new MessingItem(messagings, RecepyDetail.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.setAdapter(item);
    }
}
