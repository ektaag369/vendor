package com.example.vendor.Auth;

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

import com.example.vendor.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    Button get_otp;
    EditText mobielnumber;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        get_otp=findViewById(R.id.btn_get_otp);
        mobielnumber=findViewById(R.id.input_mobileno);
        progressBar=findViewById(R.id.progressbarsending);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mobielnumber.getText().toString().trim().isEmpty()){
                    if(mobielnumber.getText().toString().trim().length()==10){

                        progressBar.setVisibility(View.VISIBLE);
                        get_otp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + mobielnumber.getText().toString(),
                                120,
                                TimeUnit.SECONDS,
                                PhoneActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        get_otp.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        get_otp.setVisibility(View.VISIBLE);
                                        Toast.makeText(PhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("TAG", "onVerificationFailed: "+e.getMessage() );
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        progressBar.setVisibility(View.GONE);
                                        get_otp.setVisibility(View.VISIBLE);

                                        Intent i=new Intent(PhoneActivity.this, VerifyOtpActivity.class);
                                        i.putExtra("mobilenumberintent",mobielnumber.getText().toString());
                                        i.putExtra("backendotp",backendotp);
                                        startActivity(i);
                                    }
                                }
                        );


                    }
                    else{
                        Toast.makeText(PhoneActivity.this, "Mobile Number is Incorrect...", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(PhoneActivity.this, "Please Enter Mobile Number...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}