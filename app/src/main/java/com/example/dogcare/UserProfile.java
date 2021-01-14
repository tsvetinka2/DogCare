package com.example.dogcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
        TextInputLayout fullName,phone,password,email;
        TextView fullNameLabel, usernameLabel;

        //Global variables to hold user data inside the activity
        String _USERNAME, _FULLNAME, _EMAIL, _PHONE,_PASSWORD;
        Button updateBtn;
        DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        //Hooks
        fullName = findViewById(R.id.fullNameProfile);
        phone = findViewById(R.id.phoneProfile);
        password = findViewById(R.id.passwordProfile);
        email = findViewById(R.id.emailProfile);
        fullNameLabel = findViewById(R.id.fullName);
        usernameLabel = findViewById(R.id.userName);
        updateBtn = findViewById(R.id.updateBtn);

        //ShowAllData
        showAllUserData();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneChanged()){
                    Toast.makeText(UserProfile.this,"Data has been updated",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UserProfile.this, "Data is the same and can not be updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        _FULLNAME = intent.getStringExtra("fullName");
        _EMAIL = intent.getStringExtra("email");
        _PHONE = intent.getStringExtra("phone");
        _USERNAME = intent.getStringExtra("username");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_FULLNAME);
        usernameLabel.setText(_USERNAME);
        fullName.getEditText().setText(_FULLNAME);
        email.getEditText().setText(_EMAIL);
        phone.getEditText().setText(_PHONE);
        password.getEditText().setText(_PASSWORD);

    }

  /*  public void update(View view){

    }*/

    private boolean isPhoneChanged() {
        if(!_PHONE.equals(phone.getEditText().getText().toString())){
            reference.child(_USERNAME).child("password").setValue(phone.getEditText().getText().toString());
            _PHONE = phone.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!_EMAIL.equals(email.getEditText().getText().toString())){
            reference.child(_USERNAME).child("password").setValue(email.getEditText().getText().toString());
            _EMAIL = email.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if(!_PASSWORD.equals(password.getEditText().getText().toString())){
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!_FULLNAME.equals(fullName.getEditText().getText().toString())){
            reference.child(_USERNAME).child("fullName").setValue(fullName.getEditText().getText().toString());
            _FULLNAME = fullName.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }
}