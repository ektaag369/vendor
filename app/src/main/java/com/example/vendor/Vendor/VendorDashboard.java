package com.example.vendor.Vendor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.vendor.Chemicals.ChemicalDashboard;
import com.example.vendor.R;
import com.example.vendor.Seeds.SeedDashboardActivity;

public class VendorDashboard extends AppCompatActivity {

    CardView SeedModule,ChemicalModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);

        SeedModule=findViewById(R.id.seedmodule);
        ChemicalModule=findViewById(R.id.chemicalmodule);

        SeedModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VendorDashboard.this, SeedDashboardActivity.class);
                startActivity(i);
            }
        });

        ChemicalModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VendorDashboard.this, ChemicalDashboard.class);
                startActivity(i);
            }
        });


    }


}