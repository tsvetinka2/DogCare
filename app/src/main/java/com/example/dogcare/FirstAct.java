package com.example.dogcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirstAct extends AppCompatActivity {
    TextInputLayout textUsername, textPassword;
    Button loginBtn;
    TextView registerTextView, forgotPassTextView;
    FirebaseAuth firebaseAuth;


    private Boolean validateUsername(){
        String value = textUsername.getEditText().getText().toString();
        if(value.isEmpty()){
            textUsername.setError("Field can not be empty");
            return false;
        }else{
            textUsername.setError(null);
            textUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String value = textPassword.getEditText().getText().toString();
        if(value.isEmpty()){
            textPassword.setError("Field can not be empty");
            return false;
        }else{
            textPassword.setError(null);
            textPassword.setErrorEnabled(false);
            return true;
        }
    }
    private void isUser(){
        String userEnteredUsername = textUsername.getEditText().getText().toString().trim();
        String userEnteredPassword = textPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    textUsername.setError(null);
                    textUsername.setErrorEnabled(false);

                    for (DataSnapshot childrenSnapshot : snapshot.getChildren()) {
                        final String passwordFromDB = childrenSnapshot.child("password").getValue().toString();

                        if(passwordFromDB.equals(userEnteredPassword)) {
                            textUsername.setError(null);
                            textUsername.setErrorEnabled(false);

                            String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                            String fullNameFromDB = snapshot.child(userEnteredUsername).child("fullName").getValue(String.class);
                            String phoneFromDB = snapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                            String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("fullName", fullNameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("phone", phoneFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("password", passwordFromDB);

                            startActivity(intent);
                        }else{
                            textPassword.setError("Wrong Password");
                            textPassword.requestFocus();
                        }
                    }
                }else{
                    textUsername.setError("No such User exists");
                    textUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void loginUser(View view){
        if(!validateUsername() || !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        textUsername =  findViewById(R.id.edittextusername);
        textPassword =  findViewById(R.id.edittextpass);
        loginBtn = (Button) findViewById(R.id.loginbutton);
        registerTextView = (TextView) findViewById(R.id.registertextview);
        forgotPassTextView = (TextView) findViewById(R.id.forgotpasstextview);
        firebaseAuth = FirebaseAuth.getInstance();

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FirstAct.this,RegisterActivity.class));
            }
        });

       loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginUser(v);
            }
        });

        forgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstAct.this,MakeSelection.class));
            }
        });

    }
}