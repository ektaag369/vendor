package com.example.vendor.Chemicals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.vendor.R;
import com.example.vendor.Seeds.SeedsAdapterClass;
import com.example.vendor.Seeds.SeedsModelClass;
import com.example.vendor.Seeds.ShowSeeds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowChemicals extends AppCompatActivity {
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<ChemicalModelClass> chemicallist=new ArrayList<>();
    ChemicalAdapter adapter;
    FirebaseAuth mauth;
    String vendorid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chemicals);

        recyclerView=findViewById(R.id.chemicals_rv);
        mauth=FirebaseAuth.getInstance();
        vendorid=mauth.getCurrentUser().getUid();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Chemicals");
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter=new ChemicalAdapter(this,chemicallist);
        recyclerView.setAdapter(adapter);
        getChemicals();
    }

    private void getChemicals() {
        firestore.collection("Chemicals").whereEqualTo("VendorID",vendorid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            progressDialog.dismiss();
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                ChemicalModelClass c=d.toObject(ChemicalModelClass.class);
                                chemicallist.add(c);


                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(ShowChemicals.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}