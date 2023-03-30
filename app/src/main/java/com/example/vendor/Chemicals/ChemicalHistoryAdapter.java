package com.example.vendor.Chemicals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendor.R;
import com.example.vendor.Seeds.SeedHistoryAdapter;
import com.example.vendor.Seeds.SeedOrdersModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChemicalHistoryAdapter extends RecyclerView.Adapter<ChemicalHistoryAdapter.ChemicalhistoryViewholder> {
    Context context;
    ArrayList<ChemicalOrderModel> historylist;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth mauth=FirebaseAuth.getInstance();

    public ChemicalHistoryAdapter(Context context, ArrayList<ChemicalOrderModel> historylist) {
        this.context = context;
        this.historylist = historylist;
    }
    @NonNull
    @Override
    public ChemicalhistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_order_item,parent,false);
        return new ChemicalHistoryAdapter.ChemicalhistoryViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChemicalhistoryViewholder holder, int position) {
        ChemicalOrderModel pos=historylist.get(position);

        holder.prodid.setText(pos.getOrder_Product_id());
        holder.price.setText(pos.getOrder_Product_amount());
        holder.qty.setText(pos.getOrder_Product_count());
        String address=pos.getOrder_Product_address()+", "+pos.getOrder_Product_pincode();
        holder.address.setText(address);
        holder.orderid.setText(pos.getOrder_id());
        holder.paymentmode.setText(pos.getOrder_Payment_Mode());
        holder.status.setText(pos.getOrder_Status());

        holder.deliveredbtn.setVisibility(View.GONE);
        firestore.collection("Chemicals").whereEqualTo("ID",pos.getOrder_Product_id())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                holder.seedname.setText(d.getString("Name"));
                                String url=d.getString("ImageUrl");
                                if(url!=null){
                                    Picasso.get().load(url).into(holder.img);
                                }
                                else{
                                    Picasso.get().load(R.drawable.ic_launcher_background).into(holder.img);
                                }
                            }
                        }
                    }
                });

        firestore.collection("Farmers").document(pos.getOrder_Product_UserID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        holder.username.setText(documentSnapshot.getString("Name"));
                        holder.mobile.setText(documentSnapshot.getString("Phone"));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return historylist.size();
    }

    public class ChemicalhistoryViewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView seedname,prodid,price,qty,address,orderid,paymentmode,username,mobile,status;
        Button deliveredbtn;
        public ChemicalhistoryViewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.sorderimg);
            seedname=itemView.findViewById(R.id.sorderseedname);
            prodid=itemView.findViewById(R.id.sorderprodid);
            price=itemView.findViewById(R.id.sorderprice);
            qty=itemView.findViewById(R.id.sorderqty);
            address=itemView.findViewById(R.id.sorderuseraddress);
            orderid=itemView.findViewById(R.id.sorderid);
            paymentmode=itemView.findViewById(R.id.sorderpaymentmode);
            username=itemView.findViewById(R.id.sorderusername);
            mobile=itemView.findViewById(R.id.sorderusermobile);
            orderid=itemView.findViewById(R.id.sorderid);
            status=itemView.findViewById(R.id.sorderstatus);
            deliveredbtn=itemView.findViewById(R.id.sorderdeliveredbtn);


        }
    }
}
