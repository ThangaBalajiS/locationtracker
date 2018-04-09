package com.thangabalajis.studentlocationtracker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    EditText etName,etRoll,etPhone;
    SharedPreferences sharedPref;
    static String prefff = "PREF_DETAILS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();
        sharedPref = getSharedPreferences(prefff,Context.MODE_PRIVATE);

        etName = findViewById(R.id.name);
        etRoll =  findViewById(R.id.rollno);
        etPhone =  findViewById(R.id.phone);

        try {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("myAppIdstudentlocation")
                    .server("http://studentlocation.herokuapp.com/parse/")
                    .build()
            );
        } catch (Exception e) {

        }
        Log.i("onCreate: ",sharedPref.getString("rollno","12"));
        if (sharedPref.getString("rollno","12") != "12"){
            Intent intent = new Intent(this,Registered.class);
            startActivity(intent);
        }

    }



    public void registerStudent(View view){
        String studName = etName.getText().toString();
        String studRollNo = etRoll.getText().toString();
        String studPhone = etPhone.getText().toString();

        if(studName != "" && studRollNo != "" && studPhone != ""){

            ParseObject gameScore = new ParseObject("Student");
            gameScore.put("name", studName);
            gameScore.put("rollno", studRollNo);
            gameScore.put("phone", studPhone);
            gameScore.saveInBackground();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("name", studName);
            editor.putString("rollno", studRollNo);
            editor.putString("phone", studPhone);
            editor.apply();


            Intent o = new Intent(this,Registered.class);
            startActivity(o);

        }else{
            Toast.makeText(this,"Please Fill All details!",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("location_permission")

                        .setMessage("Location_permission Needed")

                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
