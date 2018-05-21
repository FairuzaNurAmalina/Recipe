package com.example.bitp3453.recipe.Recipes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bitp3453.recipe.Adapter.AdapterRecepy;
import com.example.bitp3453.recipe.Home.HomeActivity;
import com.example.bitp3453.recipe.R;
import com.example.bitp3453.recipe.Utils.Send_Recepi_ID;
import com.example.bitp3453.recipe.Utils.Utility;
import com.example.bitp3453.recipe.models.Add_recepy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class RecipesActivity extends AppCompatActivity implements Send_Recepi_ID {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private static final String TAG = "RecipesActivity";
    private static final int ACTIVITY_NUM = 2;

    EditText textTitle, textDesc, textCategory, teststep, description;
    ImageButton imagebutton, step_image;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath = null;
    TextView Save;
    String textTitle1, textDesc1, teststep1, textCategory1, indgredientval, description1;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String user_id, uuid, Stepval;
    Uri ImageUrl;
    LinearLayout show_ingredients;
    ImageView plus, plus_ingredients;
    ArrayList<String> steps = new ArrayList<>();
    ArrayList<String> indregent = new ArrayList<>();
    int pos_step = -1;
    int pos_ingredient = -1;
    String userChoosenTask, CLickImage;
    Bitmap thumbnail, SaveImage;
    AdapterRecepy recepy;

    RecyclerView recyclerView;
    ArrayList<Add_recepy> add_recepies = new ArrayList<>();
    String[] foodcat = {"American", "Chinese","French","Indian","Indonesian","Italian","Japanese","Korean","Malaysian","Mexican","Thailand","Turkish","Vietnamese","Other"};
    String selectedcat;
    int a = 0;
    ImageView imgracipeimg;
    android.support.v7.app.AlertDialog alertDialog ;
    RelativeLayout relLayout3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        textTitle = (EditText) findViewById(R.id.textTitle);
        textDesc = (EditText) findViewById(R.id.textDesc);
        textCategory = (EditText) findViewById(R.id.textCategory);
        imagebutton = (ImageButton) findViewById(R.id.tvPhoto);
        relLayout3 = (RelativeLayout)findViewById(R.id.relLayout3);
        imgracipeimg = (ImageView)findViewById(R.id.imgracipeimg);
        teststep = (EditText) findViewById(R.id.teststep);
        Save = (TextView) findViewById(R.id.Save);
        plus = (ImageView) findViewById(R.id.plus_step);
        plus_ingredients = (ImageView) findViewById(R.id.plus_ingredients);
        step_image = (ImageButton) findViewById(R.id.step_image);
        //show_edittext = (LinearLayout) findViewById(R.id.show_edittext);
        show_ingredients = (LinearLayout) findViewById(R.id.show_ingredients);
        description = (EditText) findViewById(R.id.description);
        recyclerView = (RecyclerView) findViewById(R.id.add_recepy);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        SharedPreferences preferences = getSharedPreferences("Credential", MODE_PRIVATE);
        user_id = preferences.getString("user_id", "");
        Log.e("user_id-=--=", user_id);



        imgracipeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLickImage = "R";
                selectImage();
                relLayout3.setVisibility(View.VISIBLE);
                imgracipeimg.setVisibility(View.GONE);
            }
        });
        //setupBottomNavigationView();
        //setupToolbar();

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();

            }
        });
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CLickImage = "R";
                selectImage();
            }
        });
        step_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CLickImage = "S";
                selectImage();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Add_recepy();

            }
        });
        textCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                getcat();
                return false;
            }
        });



        plus_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textDesc.getText().toString().equals("")) {
                    textDesc.setError("Cannot be null");

                } else {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row1, null);
                    TextView textOut = (TextView) addView.findViewById(R.id.textout);
                    String val_ingredient = textDesc.getText().toString();
                    textOut.setText(val_ingredient);
                    indregent.add(val_ingredient);
                    ImageView buttonRemove = (ImageView) addView.findViewById(R.id.remove);
                    pos_ingredient++;
                    buttonRemove.setTag(pos_ingredient);
                    buttonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            pos_ingredient--;
                            String ppp = String.valueOf(v1.getTag());
                            try {
                                indgredientval = steps.get(Integer.parseInt(ppp.toString()));
                            } catch (Exception e) {
                                Log.e("step", "...." + e.toString());
                            }
                            indregent.remove(indgredientval + "\n");
                            ((LinearLayout) addView.getParent()).removeView(addView);

                        }
                    });

                    show_ingredients.addView(addView);
                    textDesc.setText("");

                }


            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (filePath != null) {
                    textTitle1 = textTitle.getText().toString();
                    textDesc1 = textDesc.getText().toString();
                    teststep1 = teststep.getText().toString();
                    description1 = description.getText().toString();
                    steps.add(teststep1);
                    indregent.add(textDesc1);
                    textCategory1 = textCategory.getText().toString();
                    if (textTitle.getText().toString().equals("null") || textDesc.getText().toString().equals("null")
                            || teststep.getText().toString().equals("null") || textCategory.getText().toString().equals("null")) {
                        Toast.makeText(RecipesActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadImage();
                    }


                } else {
                    Toast.makeText(RecipesActivity.this, "Fill Recipe Image", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(RecipesActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(RecipesActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            uuid = UUID.randomUUID().toString();

            StorageReference ref = storageReference.child("images/" + uuid);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            RecipeUpload();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RecipesActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageUrl = taskSnapshot.getDownloadUrl();
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    public void getcat() {


      android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(RecipesActivity.this);
        //layout.addView(title);
        builder.setTitle("Add Category/Kategori");
        builder.setItems(foodcat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedcat = Arrays.asList(foodcat).get(i);

                textCategory.setText(selectedcat);
                if (a == 1){
                    a = 0;
                }

            }
        });
        if (a == 0) {
           alertDialog = builder.show();
        }
        if(alertDialog.isShowing())
        {
            a = 1;
        }
    }

    private void RecipeUpload() {
        databaseReference = FirebaseDatabase.getInstance().getReference("RecipeDescription");
        String uid = databaseReference.push().getKey();
        databaseReference.child(uid).child("Title").setValue(textTitle1).toString();
        databaseReference.child(uid).child("Ingredient").setValue("" + indregent).toString();
        databaseReference.child(uid).child("Step").setValue("" + steps).toString();
        databaseReference.child(uid).child("Category").setValue(textCategory1).toString();
        databaseReference.child(uid).child("RecepiImage").setValue("" + ImageUrl).toString();
        databaseReference.child(uid).child("ID").setValue(user_id).toString();
        databaseReference.child(uid).child("unique_recipe_id").setValue(uid).toString();
        databaseReference.child(uid).child("Description").setValue(description1).toString();
        Toast.makeText(RecipesActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

        Intent in = new Intent(RecipesActivity.this, HomeActivity.class);
        startActivity(in);
        finish();

    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        try {

            thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            filePath = getImageUri(getApplicationContext(), thumbnail);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);

                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                imgracipeimg.setVisibility(View.VISIBLE);
                relLayout3.setVisibility(View.GONE);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                imgracipeimg.setVisibility(View.VISIBLE);
                relLayout3.setVisibility(View.GONE);
            }


            if (CLickImage.equals("R")) {
                imagebutton.setImageBitmap(thumbnail);
                imgracipeimg.setVisibility(View.GONE);
            } else if (CLickImage.equals("S")) {
                step_image.setImageBitmap(thumbnail);
                imgracipeimg.setVisibility(View.GONE);
                SaveImage = thumbnail;
            } else {

            }
        }catch (Exception e){}
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                filePath = data.getData();
                Log.e("gallery==", "---" + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (CLickImage.equals("R")) {
            imagebutton.setImageBitmap(bm);
        } else if (CLickImage.equals("S")) {
            step_image.setImageBitmap(bm);
            SaveImage = bm;

        } else {

        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    void Add_recepy() {
        teststep1 = teststep.getText().toString();
        if (SaveImage != null || !teststep.getText().toString().equals("")) {
            UUID uid = UUID.randomUUID();
            steps.add(""+teststep1);
            add_recepies.add(new Add_recepy("" + uid, teststep1, SaveImage));
            recepy = new AdapterRecepy(add_recepies, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recepy);
            teststep.setText("");
            SaveImage = null;


        } else {
            Toast.makeText(this, "Add Recipe", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void yourDesiredMethod(String ID) {

        Log.e("ID", ID);

        add_recepies.remove("" + ID);
        recepy.notifyDataSetChanged();
    }
}
