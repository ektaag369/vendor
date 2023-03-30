package com.example.vendor.Seeds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendor.R;
import com.example.vendor.Utility.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeedsAdapterClass extends RecyclerView.Adapter<SeedsAdapterClass.SeedsViewHolder> {
    Context context;
    ArrayList<SeedsModelClass> seedslist;
    Constants constants=new Constants();
    Intent intent;

    public SeedsAdapterClass(Context context, ArrayList<SeedsModelClass> seedslist) {
        this.context = context;
        this.seedslist = seedslist;
    }

    @NonNull
    @Override
    public SeedsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_item,parent,false);
        return new SeedsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedsViewHolder holder, int position) {

        holder.name.setText(seedslist.get(position).getSeedName());
        holder.variety.setText(seedslist.get(position).getVariety());
        Double mrp=seedslist.get(position).getPrice();
        String MRP=String.format("%.2f",mrp);
        holder.price.setText(MRP);
        String url=seedslist.get(position).getImageUrl();
        if(url!=null){
            Picasso.get().load(url).into(holder.image);
        }
        else{
            Picasso.get().load(R.drawable.ic_launcher_background).into(holder.image);
        }
        holder.linearLayout.setOnClickListener(v-> {

            intent = new Intent(context, SeedDetailActivity.class);
            intent.putExtra(constants.SEED_NAME, seedslist.get(position).getSeedName());
            intent.putExtra(constants.BRAND_NAME, seedslist.get(position).getBrandName());
            intent.putExtra(constants.SEED_ID, seedslist.get(position).getSeedID());
            intent.putExtra(constants.SEED_DESCRIPTION, seedslist.get(position).getSeedDescription());
            intent.putExtra(constants.TIME_PERIOD, seedslist.get(position).getTimeperiod());
            intent.putExtra(constants.ITEM_WEIGHT, seedslist.get(position).getItemWeight());
            intent.putExtra(constants.NET_QUANTITY, seedslist.get(position).getNetQuantity());
            intent.putExtra(constants.STOCK_STATUS, seedslist.get(position).getStockStatus());
            intent.putExtra(constants.PRICE, seedslist.get(position).getPrice());
            intent.putExtra(constants.SEED_VARIETY, seedslist.get(position).getVariety());
            intent.putExtra(constants.VENDOR_ID, seedslist.get(position).getVendorID());
            intent.putExtra(constants.SEED_iMAGE_URL, seedslist.get(position).getImageUrl());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return seedslist.size();
    }

    public class SeedsViewHolder extends RecyclerView.ViewHolder{
        TextView name,variety,price;
        ImageView image;
        LinearLayout linearLayout;
        public SeedsViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.seednamerv);
            variety=itemView.findViewById(R.id.seedvarietyrv);
            price=itemView.findViewById(R.id.seedpricerv);
            image=itemView.findViewById(R.id.seedimgrv);
            linearLayout=itemView.findViewById(R.id.linearlayouttouch);
        }
    }
}
