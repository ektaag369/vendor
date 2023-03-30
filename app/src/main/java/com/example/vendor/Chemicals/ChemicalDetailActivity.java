package com.example.vendor.Chemicals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vendor.R;
import com.example.vendor.Utility.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ChemicalDetailActivity extends AppCompatActivity {
    TextView productname,price,stockstatus,cname,cvariety,netweight,netquantity
            ,brandname,use,description;
    ImageView chemicalimage;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_detail);
        // constant object
        Constants constants= new Constants();

        // views
        productname=findViewById(R.id.c_name_text);
        price=findViewById(R.id.c_price);
        stockstatus=findViewById(R.id.c_stockstatus);
        cname=findViewById(R.id.cname);
        cvariety=findViewById(R.id.c_variety);
        netweight=findViewById(R.id.citem_weight);
        netquantity=findViewById(R.id.cquantity);
        brandname=findViewById(R.id.cbrand_name);
        use=findViewById(R.id.charverstingtime);
        description=findViewById(R.id.cdescription);
        chemicalimage=findViewById(R.id.c_imageView);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

            //getting extras

            String tid = String.valueOf(bundle.get(constants.CHEMICAL_ID));
            String tname=bundle.get(constants.CHEMICAL_NAME).toString();
            String tcategory=bundle.get(constants.CHEMICAL_CATEGORY).toString();
            String timageUrl=bundle.get(constants.CHEMICAL_IMAGEURL).toString();
            String tprice=bundle.get(constants.CHEMICAL_PRICE).toString();
            String tvendorid=bundle.get(constants.CHEMICAL_VENDOR_ID).toString();
            String tnet_weight=String.valueOf(bundle.get(constants.CHEMICAL_ITEM_WEIGHT));
            String tnet_quantity=String.valueOf(bundle.get(constants.CHEMICAL_NET_QUANTITY));
            String tbrand_name=bundle.get(constants.CHEMICAL_BRAND_NAME).toString();
            String tuse=bundle.get(constants.CHEMICAL_USE).toString();
            String tdescription=bundle.get(constants.CHEMICAL_DESC).toString();
            String tstockstatus=bundle.get(constants.CHEMICAL_STOCK_STATUS).toString();


            // setting values
            if(timageUrl!=null){
                Picasso.get().load(timageUrl).into(chemicalimage);
            }
            else{
                Picasso.get().load(R.drawable.ic_launcher_background).into(chemicalimage);
            }
            productname.setText(tname+" "+tcategory);
            price.setText(tprice);
            stockstatus.setText(tstockstatus);
            if (tstockstatus.equals("In Stock")) {
                stockstatus.setTextColor(this.getResources().getColor(R.color.green));
            } else {
                stockstatus.setTextColor(this.getResources().getColor(R.color.red));
            }
            cname.setText(tname);
            cvariety.setText(tcategory);
            netweight.setText(tnet_weight);
            netquantity.setText(tnet_quantity);
            brandname.setText(tbrand_name);
            use.setText(tuse);
            description.setText(tdescription);


        }
    }
}