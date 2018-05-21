package com.example.bitp3453.recipe.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Permissions extends Activity {

    public static final int RequestPermissionCode = 1;

    private Context context;

    public Permissions(Context context) {
        this.context = context;
    }

    public void requestPermission() {


        ActivityCompat.requestPermissions((Activity) context, new String[]
                {

                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        ACCESS_FINE_LOCATION


                }, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {



                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ACCESS_FINE_LOCATION = grantResults[2] == PackageManager.PERMISSION_GRANTED;


                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE&&ACCESS_FINE_LOCATION) {

                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }




}