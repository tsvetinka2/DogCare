package com.example.dogcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetNewPassword extends AppCompatActivity {

    TextInputEditText newPassword, confirmPassword;
    Button btnOK;
    String phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        phoneNo = getIntent().getStringExtra("phone");

        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnOK = findViewById(R.id.okButton);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassword.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                if(newPass.equals(confirmPass)) {
                    //Get the new password from the field
                    String _newPassword = newPassword.getText().toString().trim();

                    //Update data in Firebase
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot == null) {
                                Toast.makeText(SetNewPassword.this, "Cannot find users.", Toast.LENGTH_SHORT).show();
                            }

                            Iterable<DataSnapshot> users = snapshot.getChildren();
                            for (DataSnapshot user : users) {
                                String test = user.child("phone").getValue().toString();
                                if (user.child("phone").getValue().toString().equals(phoneNo)) {
                                    user.child("password").getRef().setValue(_newPassword);

                                    Intent intent = new Intent(getApplicationContext(), ForgetPassSuccessMessage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}