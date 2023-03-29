package com.example.vendor.Chemicals;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vendor.R;
import com.example.vendor.Seeds.AddSeeds;
import com.example.vendor.Seeds.ShowSeeds;

public class HomeChemicalFragment extends Fragment {
    CardView sellchemicals,showchemicals;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_chemical, container, false);
        sellchemicals=view.findViewById(R.id.sellchemicalcard);
        showchemicals=view.findViewById(R.id.showchemicalcard);
        sellchemicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddChemicals.class);
                startActivity(i);
            }
        });

        showchemicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ShowChemicals.class);
                startActivity(i);
            }
        });
        return view;
    }
}