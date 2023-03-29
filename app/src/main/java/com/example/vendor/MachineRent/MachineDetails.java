package com.example.vendor.MachineRent;

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

public class MachineDetails extends AppCompatActivity {
    EditText tname,tpurpose,tdetails,tcompany,trate,tid;
    Button add_btn,showimg;
    ImageView image;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    Uri filepath;
    Bitmap bitmap;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_details);

        // xml ids
        tname=findViewById(R.id.mname);
        tpurpose=findViewById(R.id.mpurpose);
        tdetails=findViewById(R.id.mdetails);
        tcompany=findViewById(R.id.mcompany);
        trate=findViewById(R.id.mrate);
        tid=findViewById(R.id.mid);
        image=findViewById(R.id.mimg);
        showimg=findViewById(R.id.mimgbtn);
        add_btn=findViewById(R.id.maddbtn);

        // add image
        showimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(MachineDetails.this)
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
                        ||tpurpose.getText().toString().isEmpty()||tdetails.getText().toString().isEmpty()||tcompany.getText().toString().isEmpty()
                        ||trate.getText().toString().isEmpty()){

                    Toast.makeText(MachineDetails.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else{
                    add_machine(filepath);
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
                image.setImageBitmap(bitmap);
            }catch (Exception e){

            }



        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void add_machine(Uri image) {

        ProgressDialog pd = new ProgressDialog(MachineDetails.this);
        pd.setTitle("Machine Adding");
        pd.setMessage("Uploading");
        pd.show();

        String rentorid= mAuth.getCurrentUser().getUid();
        HashMap<String,Object> machines=new HashMap<>();

        machines.put("ID",tid.getText().toString());
        machines.put("Name",tname.getText().toString());
        machines.put("Purpose",tpurpose.getText().toString());
        machines.put("Details",tdetails.getText().toString());
        machines.put("Company",tcompany.getText().toString());
        machines.put("Rate",trate.getText().toString());
        machines.put("LenderID",rentorid);

        FirebaseStorage storage= FirebaseStorage.getInstance();
        String filename=getfilenamefromuri(filepath );
        StorageReference machineuploader=storage.getReference().child("/machinesimages").child(filename);

        machineuploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                machineuploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url=(String.valueOf(uri));
                        machines.put("ImageUrl",url);
                        firestore.collection("Machines").add(machines).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isComplete()){
                                    pd.dismiss();
                                    Toast.makeText(MachineDetails.this, "data uploaded successfully", Toast.LENGTH_SHORT).show();
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
        tdetails.setText("");
        tpurpose.setText("");
        tcompany.setText("");
        trate.setText("");

    }
}