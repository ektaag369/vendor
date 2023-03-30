package com.example.vendor.Chemicals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.vendor.R;
import com.example.vendor.Seeds.BookedOrderFragment;
import com.example.vendor.Seeds.HistoryOrderFragment;
import com.example.vendor.Seeds.HomeFragment;
import com.example.vendor.Seeds.ProfileFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ChemicalDashboard extends AppCompatActivity {
    MeowBottomNavigation meowBottomNavigation;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_dashboard);

        meowBottomNavigation=findViewById(R.id.meowbnvchemical);
        frameLayout=findViewById(R.id.framelayoutchemical);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.orders));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.history));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.profile));

        meowBottomNavigation.show(1,true);
        replace(new HomeChemicalFragment());

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch(model.getId()){
                    case 1:
                        replace(new HomeChemicalFragment());
                        break;

                    case 2:
                        replace(new BookedOrdersChemicalFragment());
                        break;

                    case 3:
                        replace(new HistoryChemicalFragment());
                        break;

                    case 4:
                        replace(new ProfileFragment());
                        break;

                }
                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayoutchemical,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}