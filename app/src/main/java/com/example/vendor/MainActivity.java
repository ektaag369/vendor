package com.example.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vendor.VendorModule.VendorDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
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

//        firestore.collection("Vendors").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }
}