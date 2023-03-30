package com.example.vendor.Seeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vendor.R;
import com.example.vendor.Vendor.VendorDashboard;
import com.example.vendor.Vendor.VendorDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
 TextView name, mobile,address;
 ImageView image;
 FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        name=view.findViewById(R.id.profilename);
        mobile=view.findViewById(R.id.profilemobile);
        address=view.findViewById(R.id.profiladdress);
        image=view.findViewById(R.id.profileimg);

        String vendorid=mAuth.getCurrentUser().getUid();
        ProgressDialog pd=new ProgressDialog(getContext());
        pd.setMessage("Loading Data");
        pd.show();

        firestore.collection("Vendors").document(vendorid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    pd.dismiss();
                    name.setText(documentSnapshot.getString("Name"));
                    mobile.setText(documentSnapshot.getString("Phone"));
                    address.setText(documentSnapshot.getString("Address"));
                    String url= documentSnapshot.getString("ImageUrl");
                    if(url!=null){
                        Picasso.get().load(url).into(image);
                    }
                    else{
                        Picasso.get().load(R.drawable.ic_launcher_background).into(image);
                    }
                }
            }
        });
        return view;
    }
}