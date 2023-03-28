package com.example.vendor.VendorModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vendor.R;
import com.example.vendor.VendorModule.DashBoard.VendorDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class VendorDetails extends AppCompatActivity {

    EditText user_name, user_phone, user_address;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);

        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        user_address = findViewById(R.id.user_address);
        submit = findViewById(R.id.submit_btn);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        String userid = currentuser.getUid();
        ProgressDialog pd=new ProgressDialog(VendorDetails.this);
        pd.show();
        firestore.collection("Vendors").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    pd.dismiss();
                    Intent i=new Intent(VendorDetails.this,VendorDashboard.class);
                    startActivity(i);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Intent i=new Intent(VendorDetails.this,VendorDetails.class);
                startActivity(i);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name.toString().isEmpty() || user_address.toString().isEmpty() ||
                        user_phone.toString().isEmpty()) {
                    Toast.makeText(VendorDetails.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    ProgressDialog pd1=new ProgressDialog(VendorDetails.this);
                    pd1.setCancelable(false);
                    pd1.setMessage("Adding Details...");
                    pd1.show();

                    String name = user_name.getText().toString();
                    String address = user_address.getText().toString();
                    String phone = user_phone.getText().toString();



                    HashMap<String, Object> vendordetails = new HashMap<>();
                    vendordetails.put("Name", name);
                    vendordetails.put("Address", address);
                    vendordetails.put("Phone", phone);

                    firestore.collection("Vendors").document(userid)
                            .set(vendordetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd1.dismiss();
                                    Toast.makeText(VendorDetails.this, "Added", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(VendorDetails.this, VendorDashboard.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd1.dismiss();
                                    Toast.makeText(VendorDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}