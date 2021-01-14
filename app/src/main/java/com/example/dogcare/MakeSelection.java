package com.example.dogcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MakeSelection extends AppCompatActivity {

    RelativeLayout relativeLayoutPhone, relativeLayoutEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);


        relativeLayoutPhone = findViewById(R.id.relativeLayoutPhone);
        relativeLayoutEmail = findViewById(R.id.relativeLayoutEmail);

        relativeLayoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeSelection.this,ForgetPassword.class));
            }
        });

        relativeLayoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MakeSelection.this, "This part has not been developed yet!",Toast.LENGTH_LONG).show();
            }
        });

    }
}