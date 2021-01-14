package com.example.dogcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyCode extends AppCompatActivity {
    String phoneNo;
    String verificationCode;
    Button verificationBtn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        phoneNo = getIntent().getStringExtra("phone");
        TextView sendCodeMessageTV = findViewById(R.id.sendCodeMessage);
        sendCodeMessageTV.setText("Enter one time password sent on " + phoneNo);

        verificationBtn = findViewById(R.id.verificationBtn);

        sendVerificationCodeToUser(phoneNo);

        verificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOTPCredentials();
            }
        });
    }

    public void sendVerificationCodeToUser(String phoneNo) {
        auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        verificationCode = verificationId;
                        Toast.makeText(VerifyCode.this,"Code sent",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(VerifyCode.this, "Verification completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(VerifyCode.this, "Verification failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void checkOTPCredentials() {
        PinView otpPinView = findViewById(R.id.otp);
        String otp = otpPinView.getText().toString();

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                openNewPasswordActivity();
                            } else {
                                Toast.makeText(VerifyCode.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(VerifyCode.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
        }
    }

    public void openNewPasswordActivity() {
        Intent intent = new Intent(getApplicationContext(), SetNewPassword.class);
        intent.putExtra("phone", phoneNo);
        startActivity(intent);
        finish();
    }
}