package com.example.vendor.Seeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vendor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowSeeds extends AppCompatActivity {

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<SeedsModelClass> seedlist=new ArrayList<>();
    SeedsAdapterClass seedsAdapterClass;
    FirebaseAuth mauth;
    String vendorid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_seeds);

        recyclerView=findViewById(R.id.seeds_rv);
        mauth=FirebaseAuth.getInstance();
        vendorid=mauth.getCurrentUser().getUid();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Seeds");
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        seedsAdapterClass=new SeedsAdapterClass(this,seedlist);
        recyclerView.setAdapter(seedsAdapterClass);
        getSeeds();
    }

    private void getSeeds() {
        firestore.collection("Seeds").whereEqualTo("VendorID",vendorid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            progressDialog.dismiss();
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                SeedsModelClass c=d.toObject(SeedsModelClass.class);
                                    seedlist.add(c);


                            }
                            seedsAdapterClass.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ShowSeeds.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}