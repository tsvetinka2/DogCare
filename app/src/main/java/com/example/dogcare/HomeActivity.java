package com.example.dogcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    public CardView userProfile, addDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userProfile =  findViewById(R.id.userProfile);
        addDog = findViewById(R.id.addDog);

        userProfile.setOnClickListener(this);
        addDog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.userProfile:
                i = new Intent(this, UserProfile.class);
                Intent firstActIntent = getIntent();
                i.putExtra("fullName", firstActIntent.getStringExtra("fullName"));
                i.putExtra("email", firstActIntent.getStringExtra("email"));
                i.putExtra("phone", firstActIntent.getStringExtra("phone"));
                i.putExtra("username", firstActIntent.getStringExtra("username"));
                i.putExtra("password", firstActIntent.getStringExtra("password"));
                startActivity(i);
                break;

            case R.id.addDog:
                i = new Intent(this, DogActivityNew.class);
                startActivity(i);
                break;
        }
    }
}