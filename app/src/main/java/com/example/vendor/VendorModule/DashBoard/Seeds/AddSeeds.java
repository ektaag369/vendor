package com.example.vendor.VendorModule.DashBoard.Seeds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vendor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;

public class AddSeeds extends AppCompatActivity {
    EditText seedid,seedname,seeddesc,seedprice,brandname,month,year,itemwt,netqty,seedvariety;
    TextView stock;
    Button add_btn,seedimgbtn,upload;
    ImageView seedimg;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    Uri filepath;
    Bitmap bitmap;
    String seedimgurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seeds);


        // xml ids
        seedid=findViewById(R.id.seedid);
        seedname=findViewById(R.id.seedname);
        seeddesc=findViewById(R.id.seeddescription);
        seedprice=findViewById(R.id.seedprice);
        seedvariety=findViewById(R.id.seedvariety);
        brandname=findViewById(R.id.seedbrandname);
        month=findViewById(R.id.seedmonth);
        year=findViewById(R.id.seedyear);
        itemwt=findViewById(R.id.seeditemwt);
        netqty=findViewById(R.id.seednetqty);
        stock=findViewById(R.id.seedstockstatus);
        add_btn=findViewById(R.id.seedaddbtn);
        spinner=findViewById(R.id.seedspinnerstock);
        seedimg=findViewById(R.id.seedimg);
        seedimgbtn=findViewById(R.id.seedimgbtn);

        // spinner
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.stock, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                stock.setText(status);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // add image
        seedimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AddSeeds.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Images"),101);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        // add data to firebase

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seedid.getText().toString().isEmpty()||brandname.getText().toString().isEmpty()
                        ||seedname.getText().toString().isEmpty()||seedvariety.getText().toString().isEmpty()||seeddesc.getText().toString().isEmpty()
                        ||seedprice.getText().toString().isEmpty()||month.getText().toString().isEmpty()||year.getText().toString().isEmpty()
                        ||stock.getText().toString().isEmpty()||itemwt.getText().toString().isEmpty()
                        ||netqty.getText().toString().isEmpty()){

                    Toast.makeText(AddSeeds.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    add_seeds(filepath);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==101 && resultCode==RESULT_OK){
            filepath=data.getData();

            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                seedimg.setImageBitmap(bitmap);
            }catch (Exception e){

            }



        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void add_seeds(Uri image) {

        ProgressDialog pd = new ProgressDialog(AddSeeds.this);
        pd.setTitle("Seeds Adding");
        pd.setMessage("Uploading");
        pd.show();

        String vendorid= mAuth.getCurrentUser().getUid();
        HashMap<String,Object> seeds=new HashMap<>();

        seeds.put("SeedID",seedid.getText().toString());
        seeds.put("BrandName",brandname.getText().toString());
        seeds.put("SeedName",seedname.getText().toString());
        seeds.put("SeedDescription",seeddesc.getText().toString());
        seeds.put("MonthHarvesting",month.getText().toString());
        seeds.put("YearHarvesting",year.getText().toString());
        seeds.put("ItemWeight",itemwt.getText().toString());
        seeds.put("NetQuantity",netqty.getText().toString());
        seeds.put("StockStatus",stock.getText().toString());
        seeds.put("Price",Double.parseDouble(seedprice.getText().toString()));
        seeds.put("VendorID",vendorid);
        seeds.put("Variety",seedvariety.getText().toString());

        FirebaseStorage storage= FirebaseStorage.getInstance();
        String filename=getfilenamefromuri(filepath );
        StorageReference seeduploader=storage.getReference().child("/seedimages").child(filename);

        seeduploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                seeduploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        seedimgurl=(String.valueOf(uri));
                        seeds.put("ImageUrl",seedimgurl);
                        firestore.collection("Seeds").add(seeds).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isComplete()){
                                    pd.dismiss();
                                    Toast.makeText(AddSeeds.this, "data uploaded successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

    }

    @SuppressLint("Range")
    private String getfilenamefromuri(Uri filepath) {
        String result=null;
        if(filepath.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(filepath,null,null,null,null );
            try{
                if(cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=filepath.getPath();
            int cut=result.lastIndexOf('/');
            if(cut!= -1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }

    private void emptyFields() {
        seedid.setText("");
        brandname.setText("");
        seedname.setText("");
        seeddesc.setText("");
        seedprice.setText("");
        stock.setText("");
        netqty.setText("");
        itemwt.setText("");
        month.setText("");
        year.setText("");
        seedvariety.setText("");
    }
}