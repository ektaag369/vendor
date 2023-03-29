package com.example.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vendor.MachineRent.MachineRenterDetails;
import com.example.vendor.Vendor.VendorDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    Button vendor,machine,harvester;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vendor=findViewById(R.id.vendor);
        machine=findViewById(R.id.MachineLender);
        harvester=findViewById(R.id.PostHarvester);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        String userid = currentuser.getUid();

        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, VendorDetails.class);
                startActivity(i);
            }
        });

        machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, MachineRenterDetails.class);
                startActivity(i);
            }
        });


    }
}