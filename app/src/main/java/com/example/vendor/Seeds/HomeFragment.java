package com.example.vendor.Seeds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.vendor.R;

public class HomeFragment extends Fragment {

    CardView sellseed,showseed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        sellseed=view.findViewById(R.id.sellseedcard);
        showseed=view.findViewById(R.id.showseedcard);
        sellseed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddSeeds.class);
                startActivity(i);
            }
        });

        showseed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ShowSeeds.class);
                startActivity(i);
            }
        });
        return view;
    }
}