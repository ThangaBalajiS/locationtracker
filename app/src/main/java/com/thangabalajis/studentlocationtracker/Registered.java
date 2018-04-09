package com.thangabalajis.studentlocationtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static com.thangabalajis.studentlocationtracker.MainActivity.prefff;

public class Registered extends AppCompatActivity {


    TextView tvName,tvRoll,tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);


        tvName = findViewById(R.id.name);
        tvRoll = findViewById(R.id.rollno);
        tvPhone = findViewById(R.id.phone);

        SharedPreferences sharedPref = getSharedPreferences(prefff,Context.MODE_PRIVATE);

        Log.e("TAG",  sharedPref.getString("name","ds"));

        tvName.setText(sharedPref.getString("name","user"));
        tvRoll.setText(sharedPref.getString("rollno","15MX41"));
        tvPhone.setText(sharedPref.getString("phone","98765432456"));
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("rollno",sharedPref.getString("rollno","15MX41"));
        startService(intent);
    }
}
