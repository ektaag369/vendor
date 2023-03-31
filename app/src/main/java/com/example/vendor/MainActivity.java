package com.example.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vendor.MachineRent.MachineRenterDetails;
import com.example.vendor.Vendor.VendorDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView vendor,machine,harvester;
    CardView card1, card2, card3;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vendor=findViewById(R.id.vendor);
        machine=findViewById(R.id.MachineLender);
        harvester=findViewById(R.id.PostHarvester);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        String userid = currentuser.getUid();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, VendorDetails.class);
                startActivity(i);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, MachineRenterDetails.class);
                startActivity(i);
            }
        });

//        card3.setOnClickListener();


    }
}