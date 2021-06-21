package com.example.verifiyotp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class SendOtpActivity extends AppCompatActivity {
    private EditText enternumber;
    private Button getotp;
    ProgressBar progressBar;
    FirebaseAuth auth;


    String url="http://clients.jprportal.com/sarju/api/user_login/?api_key=w0fp55cIdJ6lLuOqVEd251zKw6lnNd";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);

        enternumber = findViewById(R.id.enterNumber);
        getotp = findViewById(R.id.getotp);
        progressBar = findViewById(R.id.prograssbars);

       getData();

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if (enternumber.getText().toString().trim().length() == 10) {
                        progressBar.setVisibility(View.VISIBLE);
                        enternumber.setVisibility(v.INVISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+enternumber.getText().toString(), 60
                                , TimeUnit.SECONDS, SendOtpActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        enternumber.setVisibility(v.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {

                                        progressBar.setVisibility(View.GONE);
                                        enternumber.setVisibility(v.VISIBLE);
                                        Toast.makeText(SendOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull @NotNull String backendotp, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        Toast.makeText(SendOtpActivity.this, "Verify  your OTP", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SendOtpActivity.this, VerificationActivity.class);
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                    } else {
                        Toast.makeText(SendOtpActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SendOtpActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getData() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(SendOtpActivity.this, "status :"+response.getInt("status")
                            +"," +"message :"+response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SendOtpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}