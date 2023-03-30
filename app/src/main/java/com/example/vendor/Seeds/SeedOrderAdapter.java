package com.example.vendor.Seeds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendor.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SeedOrderAdapter extends RecyclerView.Adapter<SeedOrderAdapter.SeedOrderViewholder> {
    Context context;
    ArrayList<SeedOrdersModel> orderlist;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    public SeedOrderAdapter(Context context, ArrayList<SeedOrdersModel> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public SeedOrderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_order_item,parent,false);
        return new SeedOrderAdapter.SeedOrderViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedOrderViewholder holder, int position) {
        SeedOrdersModel pos=orderlist.get(position);
        holder.prodid.setText(pos.getOrder_Product_id());
        holder.price.setText(pos.getOrder_Product_amount());
        holder.qty.setText(pos.getOrder_Product_count());
        holder.address.setText(pos.getOrder_Product_address());
        holder.pincode.setText(pos.getOrder_Product_pincode());

        firestore.collection("Seeds").whereEqualTo("SeedID",pos.getOrder_Product_id())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d:list){
                                holder.seedname.setText(d.getString("SeedName"));
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
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class SeedOrderViewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView seedname,prodid,price,qty,address,pincode;
        public SeedOrderViewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.sorderimg);
            seedname=itemView.findViewById(R.id.sordername);
            prodid=itemView.findViewById(R.id.sorder_prod_id);
            price=itemView.findViewById(R.id.sorderprice);
            qty=itemView.findViewById(R.id.sorderqty);
            address=itemView.findViewById(R.id.sorderaddress);
            pincode=itemView.findViewById(R.id.sorderpincode);
        }
    }
}
