package com.example.vendor.Seeds;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vendor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HistoryOrderFragment extends Fragment {
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ArrayList<SeedOrdersModel> historylist=new ArrayList<>();
    SeedHistoryAdapter adapter;
    FirebaseAuth mauth;
    String vendorid;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history_order, container, false);

        recyclerView=view.findViewById(R.id.seeds_history_rv);
        mauth=FirebaseAuth.getInstance();
        vendorid=mauth.getCurrentUser().getUid();

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Orders");
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter=new SeedHistoryAdapter(getContext(),historylist);
        recyclerView.setAdapter(adapter);
        getOrders();
        return view;
    }
    private void getOrders() {
        firestore.collection("Vendors").document(vendorid).collection("Orders").whereEqualTo("Order_Status","Delivered").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            progressDialog.dismiss();
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                SeedOrdersModel c=d.toObject(SeedOrdersModel.class);
                                String documentId=d.getId();
                                c.setDocumentId(documentId);
                                historylist.add(c);


                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}