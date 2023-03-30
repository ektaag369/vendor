package com.example.vendor.Chemicals;

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
import com.example.vendor.Seeds.AddSeeds;
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

public class AddChemicals extends AppCompatActivity {
    EditText tid,tname,tdesc,tprice,tbrand,tuse,titemwt,tnetqty;
     TextView tstock,tcategory;
    Button add_btn,chemicalimgbtn;
    ImageView chemicalimg;
    Spinner spinnerstock, spinnercategory;
    ArrayAdapter arrayAdapter;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    Uri filepath;
    Bitmap bitmap;
    String chemicalimgurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chemicals);

        // xml ids
        tid=findViewById(R.id.chemicalid);
        tname=findViewById(R.id.chemicalname);
        tdesc=findViewById(R.id.chemicaldescription);
        tprice=findViewById(R.id.chemicalprice);
        tbrand=findViewById(R.id.chemicalbrandname);
        tuse=findViewById(R.id.chemicaluse);
        titemwt=findViewById(R.id.chemicalitemwt);
        tnetqty=findViewById(R.id.chemicalnetqty);
        tstock=findViewById(R.id.chemicalstockstatus);
        tcategory=findViewById(R.id.chemicalcategory);
        add_btn=findViewById(R.id.chemicaladdbtn);
        spinnercategory=findViewById(R.id.chemicalcategoryspinner);
        spinnerstock=findViewById(R.id.chemicalspinnerstock);
        chemicalimg=findViewById(R.id.chemicalimg);
        chemicalimgbtn=findViewById(R.id.chemicalimgbtn);

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.stock, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstock.setAdapter(arrayAdapter);
        spinnerstock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                tstock.setText(status);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.chemical_category, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercategory.setAdapter(arrayAdapter);
        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                tcategory.setText(status);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // add image
        chemicalimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(AddChemicals.this)
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
                if(tid.getText().toString().isEmpty()||tname.getText().toString().isEmpty()
                        ||tdesc.getText().toString().isEmpty()||tprice.getText().toString().isEmpty()||tbrand.getText().toString().isEmpty()
                        ||tuse.getText().toString().isEmpty()||titemwt.getText().toString().isEmpty()||tnetqty.getText().toString().isEmpty()
                        ||tstock.getText().toString().isEmpty()||tcategory.getText().toString().isEmpty()){

                    Toast.makeText(AddChemicals.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    add_chemicals(filepath);
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
                chemicalimg.setImageBitmap(bitmap);
            }catch (Exception e){

            }



        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void add_chemicals(Uri image) {

        ProgressDialog pd = new ProgressDialog(AddChemicals.this);
        pd.setTitle("Chemicals Adding");
        pd.setMessage("Uploading");
        pd.show();

        String vendorid= mAuth.getCurrentUser().getUid();
        HashMap<String,Object> chemicals=new HashMap<>();

        chemicals.put("ID",tid.getText().toString());
        chemicals.put("Name",tname.getText().toString());
        chemicals.put("Desc",tdesc.getText().toString());
        chemicals.put("Price",Double.parseDouble(tprice.getText().toString()));
        chemicals.put("Brand",tbrand.getText().toString());
        chemicals.put("Use",tuse.getText().toString());
        chemicals.put("NetQuantity",tnetqty.getText().toString());
        chemicals.put("ItemWeight",titemwt.getText().toString());
        chemicals.put("Stock",tstock.getText().toString());
        chemicals.put("VendorID",vendorid);
        chemicals.put("Category",tcategory.getText().toString());

        FirebaseStorage storage= FirebaseStorage.getInstance();
        String filename=getfilenamefromuri(filepath );
        StorageReference seeduploader=storage.getReference().child("/chemicalimages").child(filename);

        seeduploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                seeduploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        chemicalimgurl=(String.valueOf(uri));
                        chemicals.put("ImageUrl",chemicalimgurl);
                        firestore.collection("Chemicals").add(chemicals).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isComplete()){
                                    pd.dismiss();
                                    Toast.makeText(AddChemicals.this, "data uploaded successfully", Toast.LENGTH_SHORT).show();
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
        tid.setText("");
        tname.setText("");
        tdesc.setText("");
        tprice.setText("");
        tbrand.setText("");
        tuse.setText("");
        titemwt.setText("");
        tnetqty.setText("");
        tstock.setText("");
        tcategory.setText("");
    }
}