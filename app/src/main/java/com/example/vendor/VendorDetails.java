package com.example.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class VendorDetails extends AppCompatActivity {

    EditText user_name, user_phone, user_address;
    TextView user_gender;
    Button submit;
    FirebaseAuth mAuth;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);

        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        user_gender = findViewById(R.id.user_gender);
        user_address = findViewById(R.id.user_address);
        submit = findViewById(R.id.submit_btn);
        spinner = findViewById(R.id.spinner);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mAuth.getCurrentUser();

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
                user_gender.setText(gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name.toString().isEmpty() || user_address.toString().isEmpty() ||
                        user_phone.toString().isEmpty() || user_gender.toString().isEmpty()) {
                    Toast.makeText(VendorDetails.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressDialog pd=new ProgressDialog(VendorDetails.this);
                    pd.setCancelable(false);
                    pd.setMessage("Adding Details...");
                    pd.show();

                    String name = user_name.getText().toString();
                    String address = user_address.getText().toString();
                    String phone = user_phone.getText().toString();
                    String gender = user_gender.getText().toString();

                    String userid = currentuser.getUid();

                    HashMap<String, Object> vendordetails = new HashMap<>();
                    vendordetails.put("Name", name);
                    vendordetails.put("Address", address);
                    vendordetails.put("Phone", phone);
                    vendordetails.put("Gender", gender);

                    firestore.collection("Vendors").document("VendorDetails")
                            .collection("ListOfVendors").document(userid).set(vendordetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(VendorDetails.this, "Added", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(VendorDetails.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(VendorDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}