package com.example.verifiyotp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerificationActivity<PhoneAuthCredential> extends AppCompatActivity {
    private EditText Ed1, Ed2, Ed3, Ed4, Ed5, Ed6;
    TextView textView, resendotp;
    private Button submit;
    String getotpbackend;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Ed1 = findViewById(R.id.inputotp1);
        Ed2 = findViewById(R.id.inputotp2);
        Ed3 = findViewById(R.id.inputotp3);
        Ed4 = findViewById(R.id.inputotp4);
        Ed5 = findViewById(R.id.inputotp5);
        Ed6 = findViewById(R.id.inputotp6);
        resendotp = findViewById(R.id.textresendotp);
        submit = findViewById(R.id.Submitotp);
        textView = findViewById(R.id.mobilenumbershow);
        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));
        getotpbackend = getIntent().getStringExtra("backendotp");
        progressBar = findViewById(R.id.prograssbarv);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Ed1.getText().toString().trim().isEmpty() && !Ed2.getText().toString().trim().isEmpty() &&
                        !Ed3.getText().toString().trim().isEmpty() && !Ed4.getText().toString().trim().isEmpty()
                        && !Ed5.getText().toString().trim().isEmpty() && !Ed6.getText().toString().trim().isEmpty()) {

                    String entercodeotp = Ed1.getText().toString() + Ed2.getText().toString() + Ed3.getText().toString() +
                            Ed4.getText().toString() + Ed5.getText().toString() + Ed6.getText().toString();


                    if (getotpbackend != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);
                        com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, entercodeotp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                submit.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(VerificationActivity.this, "OTP Verify  & your register Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(VerificationActivity.this, StartActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(VerificationActivity.this, "Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    } else {
                        Toast.makeText(VerificationActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(VerificationActivity.this, "Please enter all 6 numbers", Toast.LENGTH_SHORT).show();
                }


            }
        });
        numberotpmove();
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                PhoneAuthProvider.getInstance(firebaseAuth).verifyPhoneNumber(getIntent().getStringExtra("mobile"), 60
                        , TimeUnit.SECONDS, VerificationActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull com.google.firebase.auth.PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {


                                Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull @NotNull String newbackendotp, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getotpbackend = newbackendotp;
                                Toast.makeText(VerificationActivity.this, "OTP Resend on your Phone", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    private void numberotpmove() {

        Ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    Ed2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    Ed3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    Ed4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    Ed5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().trim().isEmpty()) {
                    Ed6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}