package com.example.dogcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //Variables
    TextInputLayout regFullName, regEmail, regPhone, regUsername, regPassword;
    Button regButton;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private Boolean validateName(){
        String value = regFullName.getEditText().getText().toString();
        if(value.isEmpty()){
            regFullName.setError("Field can not be empty");
            return false;
        }else{
            regFullName.setError(null);
            regFullName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUsername(){
        String value = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(value.isEmpty()){
            regUsername.setError("Field can not be empty");
            return false;
        }else if(value.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }else if(!value.matches(noWhiteSpace)){
            regUsername.setError("White spaces are not allowed");
            return false;
        }else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String value = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(value.isEmpty()){
            regEmail.setError("Field can not be empty");
            return false;
        }else if(!value.matches(emailPattern)){
            regEmail.setError("Invalid e-mail address");
            return false;
        }else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhone(){
        String value = regPhone.getEditText().getText().toString();
        if(value.isEmpty()){
            regPhone.setError("Field can not be empty");
            return false;
        }else{
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String value = regPassword.getEditText().getText().toString();
        String passwordValidation = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                ".{4,}" +               //at least 4 characters
                "$";

        if(value.isEmpty()){
            regPassword.setError("Field can not be empty");
            return false;
        }else if(!value.matches(passwordValidation)){
            regPassword.setError("Password is too weak");
            return false;
        }else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks to all elements in activity_register.xml

        regFullName = findViewById(R.id.edittextfullname);
        regEmail = findViewById(R.id.editextemail);
        regPhone = findViewById(R.id.edittextphone);
        regUsername = findViewById(R.id.edittextusername);
        regPassword = findViewById(R.id.edittextpass);

        firebaseAuth = FirebaseAuth.getInstance();
        regButton = findViewById(R.id.registerButton);

        //Save data in FireBase on button Click

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                if(!validateName() || !validateUsername() || !validateEmail() || !validatePhone() || !validatePassword()){
                    return;
                }
                //Get all the values
                String fullName = regFullName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelper helper = new UserHelper(fullName, email, phone, username, password);

                reference.child(username).setValue(helper);
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User is created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),FirstAct.class));
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }
}