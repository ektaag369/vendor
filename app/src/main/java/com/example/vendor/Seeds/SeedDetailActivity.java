package com.example.vendor.Seeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vendor.R;
import com.example.vendor.Utility.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class SeedDetailActivity extends AppCompatActivity {
    TextView productname,price,stockstatus,seedname,seedvariety,netweight,netquantity
            ,brandname,harvestingtime,description;
    ImageView seedimage;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_detail);


        // constant object
        Constants constants= new Constants();

        // views
        productname=findViewById(R.id.product_name_text);
        price=findViewById(R.id.price);
        stockstatus=findViewById(R.id.stockstatus);
        seedname=findViewById(R.id.seedname);
        seedvariety=findViewById(R.id.seed_variety);
        netweight=findViewById(R.id.item_weight);
        netquantity=findViewById(R.id.quantity);
        brandname=findViewById(R.id.brand_name);
        harvestingtime=findViewById(R.id.harverstingtime);
        description=findViewById(R.id.description);
        seedimage=findViewById(R.id.imageView);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

            //getting extras

            String tseedid = String.valueOf(bundle.get(constants.SEED_ID));
            String tseedname=bundle.get(constants.SEED_NAME).toString();
            String tseedvariety=bundle.get(constants.SEED_VARIETY).toString();
            String timageUrl=bundle.get(constants.SEED_iMAGE_URL).toString();
            String tseedprice=bundle.get(constants.PRICE).toString();
            String tvendorid=bundle.get(constants.VENDOR_ID).toString();
            String tnet_weight=String.valueOf(bundle.get(constants.ITEM_WEIGHT));
            String tnet_quantity=String.valueOf(bundle.get(constants.NET_QUANTITY));
            String tbrand_name=bundle.get(constants.BRAND_NAME).toString();
            String tharvesting_Time=bundle.get(constants.TIME_PERIOD).toString();
            String tdescription=bundle.get(constants.SEED_DESCRIPTION).toString();
            String tstockstatus=bundle.get(constants.STOCK_STATUS).toString();


            // setting values
            if(timageUrl!=null){
                Picasso.get().load(timageUrl).into(seedimage);
            }
            else{
                Picasso.get().load(R.drawable.ic_launcher_background).into(seedimage);
            }
            productname.setText(tseedname+" "+tseedvariety);
            price.setText(tseedprice);
            stockstatus.setText(tstockstatus);
            if (tstockstatus.equals("In Stock")) {
                stockstatus.setTextColor(this.getResources().getColor(R.color.green));
            } else {
                stockstatus.setTextColor(this.getResources().getColor(R.color.red));
            }
            seedname.setText(tseedname);
            seedvariety.setText(tseedvariety);
            netweight.setText(tnet_weight);
            netquantity.setText(tnet_quantity);
            brandname.setText(tbrand_name);
            harvestingtime.setText(tharvesting_Time);
            description.setText(tdescription);


        }
    }
}